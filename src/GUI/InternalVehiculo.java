/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import Gestion_Listas.ListaVehiculos;
import Gestion_Vehiculo.*;
import java.util.ArrayList;
import Util.UtilGui;
/**
 *
 * @author Joan
 */
public class InternalVehiculo extends javax.swing.JInternalFrame {
  private ListaVehiculos listaVehiculos;

    /**
     * Creates new form InternalVehiculo
     */
    public InternalVehiculo() {
     super("Gestionar Vehículos", true, true, true, true);
       listaVehiculos = new ListaVehiculos();
       initComponents();
         configurarComponentes();
        cargarDatosTabla();
}
    public InternalVehiculo(ListaVehiculos listaCompartida) {
        super("Gestionar Vehículos", true, true, true, true);
        this.listaVehiculos = listaCompartida;
        initComponents();
        configurarComponentes();
        cargarDatosTabla();
    }
  public void setList(ListaVehiculos listaVehiculos){
        this.listaVehiculos = listaVehiculos;
    }      
private void cargarDatosTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tablaVehiculos.getModel();
        modelo.setRowCount(0);
        
        ArrayList<Vehiculo> vehiculos = listaVehiculos.obtenerTodos();
        
        for (Vehiculo v : vehiculos) {
            Object[] fila = {
                v.getPlaca(),
                v.getMarca(),
                v.getModelo(),
                v.getAño(),
                v.getTipo().getDescripcion(),
                v.getEstado().getDescripcion()
            };
            modelo.addRow(fila);
        }
    }
   private void configurarComponentes() {
       
        cmbTipo.removeAllItems();
        for (TipoVehiculo tipo : TipoVehiculo.values()) {
            cmbTipo.addItem(tipo.getDescripcion());
        }
        
        cmbEstado.removeAllItems();
        for (EstadoVehiculo estado : EstadoVehiculo.values()) {
            cmbEstado.addItem(estado.getDescripcion());
        }
        
       
        tablaVehiculos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosFormulario();
            }
        });
    }
    private void cargarDatosFormulario() {
        int filaSeleccionada = tablaVehiculos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            DefaultTableModel modelo = (DefaultTableModel) tablaVehiculos.getModel();
            String placa = (String) modelo.getValueAt(filaSeleccionada, 0);
            Vehiculo vehiculo = listaVehiculos.buscar(placa);
            
            if (vehiculo != null) {
                txtPlaca.setText(vehiculo.getPlaca());
                txtMarca.setText(vehiculo.getMarca());
                txtModelo.setText(vehiculo.getModelo());
                txtAno.setText(String.valueOf(vehiculo.getAño()));
                
               
                for (int i = 0; i < cmbTipo.getItemCount(); i++) {
                    if (cmbTipo.getItemAt(i).equals(vehiculo.getTipo().getDescripcion())) {
                        cmbTipo.setSelectedIndex(i);
                        break;
                    }
                }
                
               
                for (int i = 0; i < cmbEstado.getItemCount(); i++) {
                    if (cmbEstado.getItemAt(i).equals(vehiculo.getEstado().getDescripcion())) {
                        cmbEstado.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
    }
   
    
    private boolean validarCampos() {
        if (!UtilGui.validateRequiere(txtPlaca, txtMarca, txtModelo, txtAno)) {
        UtilGui.showErrorMessage(this, "Todos los campos son obligatorios", "Error de Validación");
        return false;
        }
        
        try {
            int año = Integer.parseInt(txtAno.getText().trim());
            if (!Vehiculo.validarAño(año)) {
               UtilGui.showErrorMessage(this, 
                "⚠️ AÑO INVÁLIDO ⚠️\n\n" +
                "El año debe cumplir:\n" +
                "• No mayor al año actual (" + java.time.LocalDate.now().getYear() + ")\n" +
                "• Máximo 20 años de antigüedad\n\n" +
                "Año ingresado: " + año + "\n" +
                "Rango válido: " + (java.time.LocalDate.now().getYear() - 20) + " - " + java.time.LocalDate.now().getYear(), 
                "Error de Validación");
            return false;
            }
        } catch (NumberFormatException e) {
            UtilGui.showErrorMessage(this, "El año debe ser un número válido", "Error de Formato");
            return false;
        }
        
        return true;
    }
    
    private void limpiarFormulario() {
        txtPlaca.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtAno.setText("");
        cmbTipo.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);
        tablaVehiculos.clearSelection();
    
}
     private void mostrarDetallesCompletos() {
        int fila = tablaVehiculos.getSelectedRow();
        if (fila >= 0) {
            String placa = (String) tablaVehiculos.getValueAt(fila, 0);
            Vehiculo v = listaVehiculos.buscar(placa);
            
            if (v != null) {
                String detalles = String.format(
                    "╔══════════════════════════════════╗\n" +
                    "║       DETALLES DEL VEHÍCULO       ║\n" +
                    "╚══════════════════════════════════╝\n\n" +
                    "🚗 INFORMACIÓN BÁSICA:\n" +
                    "   • Placa: %s\n" +
                    "   • Marca: %s\n" +
                    "   • Modelo: %s\n" +
                    "   • Año: %d\n" +
                    "   • Tipo: %s\n\n" +
                    "⚡ ESTADO ACTUAL:\n" +
                    "   • Estado: %s\n" +
                    "   • Disponible: %s\n" +
                    "   • En alquiler: %s\n\n" +
                    "🕐 INFORMACIÓN TEMPORAL:\n" +
                    "   • Antigüedad: %d años\n" +
                    "   • Año válido: %s\n\n" +
                    "📋 REPRESENTACIÓN COMPLETA:\n" +
                    "%s",
                    v.getPlaca(),
                    v.getMarca(),
                    v.getModelo(),
                    v.getAño(),
                    v.getTipo().getDescripcion(),
                    v.getEstado().getDescripcion(),
                    v.estaDisponible() ? "✅ SÍ" : "❌ NO",        
                    v.estaAlquilado() ? "⚠️ SÍ" : "✅ NO",           
                    v.calcularAntiguedad(),                        
                    Vehiculo.validarAño(v.getAño()) ? "✅ SÍ" : "❌ NO", 
                    v.toString()                                 
                );
                
                JOptionPane.showMessageDialog(this, detalles, 
                    "Detalles Completos - " + v.getPlaca(), JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    
    private void mostrarSoloDisponibles() {
        DefaultTableModel modelo = (DefaultTableModel) tablaVehiculos.getModel();
        modelo.setRowCount(0);
        
        int disponibles = 0;
        for (Vehiculo v : listaVehiculos.obtenerTodos()) {
            if (v.estaDisponible()) { 
                Object[] fila = {
                    v.getPlaca(), v.getMarca(), v.getModelo(),
                    v.getAño(), v.getTipo().getDescripcion(),
                    v.getEstado().getDescripcion()
                };
                modelo.addRow(fila);
                disponibles++;
            }
        }
        
        JOptionPane.showMessageDialog(this, 
            "✅ Mostrando " + disponibles + " vehículos DISPONIBLES");
    }
    
  
    private void mostrarSoloAlquilados() {
        DefaultTableModel modelo = (DefaultTableModel) tablaVehiculos.getModel();
        modelo.setRowCount(0);
        
        int alquilados = 0;
        for (Vehiculo v : listaVehiculos.obtenerTodos()) {
            if (v.estaAlquilado()) { 
                Object[] fila = {
                    v.getPlaca(), v.getMarca(), v.getModelo(),
                    v.getAño(), v.getTipo().getDescripcion(),
                    v.getEstado().getDescripcion()
                };
                modelo.addRow(fila);
                alquilados++;
            }
        }
        
        JOptionPane.showMessageDialog(this, 
            "⚠️ Mostrando " + alquilados + " vehículos EN ALQUILER");
    }
    
   
    private void filtrarPorAntiguedad() {
        String input = JOptionPane.showInputDialog(this, 
            "🕐 Ingrese antigüedad MÁXIMA (años):");
        
        if (input != null && !input.trim().isEmpty()) {
            try {
                int maxAntiguedad = Integer.parseInt(input);
                DefaultTableModel modelo = (DefaultTableModel) tablaVehiculos.getModel();
                modelo.setRowCount(0);
                
                int encontrados = 0;
                for (Vehiculo v : listaVehiculos.obtenerTodos()) {
                    if (v.calcularAntiguedad() <= maxAntiguedad) { // TU MÉTODO
                        Object[] fila = {
                            v.getPlaca(), v.getMarca(), v.getModelo(),
                            v.getAño(), v.getTipo().getDescripcion(),
                            v.getEstado().getDescripcion()
                        };
                        modelo.addRow(fila);
                        encontrados++;
                    }
                }
                
                JOptionPane.showMessageDialog(this, 
                    "🔍 Encontrados " + encontrados + " vehículos con máximo " + maxAntiguedad + " años");
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "❌ Ingrese un número válido");
            }
        }
    }
    
    
    private void mostrarVehiculosNuevos() {
        DefaultTableModel modelo = (DefaultTableModel) tablaVehiculos.getModel();
        modelo.setRowCount(0);
        
        int nuevos = 0;
        for (Vehiculo v : listaVehiculos.obtenerTodos()) {
            if (v.calcularAntiguedad() <= 3) { 
                Object[] fila = {
                    v.getPlaca(), v.getMarca(), v.getModelo(),
                    v.getAño(), v.getTipo().getDescripcion(),
                    v.getEstado().getDescripcion()
                };
                modelo.addRow(fila);
                nuevos++;
            }
        }
        
        JOptionPane.showMessageDialog(this, 
            "✨ Mostrando " + nuevos + " vehículos NUEVOS (≤3 años)");
    }
    
    
    private void filtrarPorTipo() {
        String[] tipos = {"Todos", "Sedán", "SUV", "Pick-up"};
        String seleccion = (String) JOptionPane.showInputDialog(
            this,
            "Seleccione el tipo de vehículo:",
            "Filtrar por Tipo",
            JOptionPane.QUESTION_MESSAGE,
            null,
            tipos,
            tipos[0]
        );
        
        if (seleccion != null) {
            if (seleccion.equals("Todos")) {
                cargarDatosTabla();
                return;
            }
            
            DefaultTableModel modelo = (DefaultTableModel) tablaVehiculos.getModel();
            modelo.setRowCount(0);
            
            int encontrados = 0;
            for (Vehiculo v : listaVehiculos.obtenerTodos()) {
                if (v.getTipo().getDescripcion().equals(seleccion)) {
                    Object[] fila = {
                        v.getPlaca(), v.getMarca(), v.getModelo(),
                        v.getAño(), v.getTipo().getDescripcion(),
                        v.getEstado().getDescripcion()
                    };
                    modelo.addRow(fila);
                    encontrados++;
                }
            }
            
            JOptionPane.showMessageDialog(this, 
                "🚗 Mostrando " + encontrados + " vehículos tipo " + seleccion);
        }
    }
    
   
    private void mostrarEstadisticas() {
        ArrayList<Vehiculo> vehiculos = listaVehiculos.obtenerTodos();
        
        int total = vehiculos.size();
        int disponibles = 0;
        int alquilados = 0;
        int enMantenimiento = 0;
        int sedanes = 0, suvs = 0, pickups = 0;
        int nuevos = 0, viejos = 0;
        double promedioAntiguedad = 0;
        
        for (Vehiculo v : vehiculos) {
           
            if (v.estaDisponible()) disponibles++;
            if (v.estaAlquilado()) alquilados++;
            if (v.getEstado() == EstadoVehiculo.EN_MANTENIMIENTO) enMantenimiento++;
            
           
            switch (v.getTipo()) {
                case SEDAN: sedanes++; break;
                case SUV: suvs++; break;
                case PICKUP: pickups++; break;
            }
            
           
            int antiguedad = v.calcularAntiguedad();
            promedioAntiguedad += antiguedad;
            if (antiguedad <= 5) nuevos++;
            else viejos++;
        }
        
        promedioAntiguedad = total > 0 ? promedioAntiguedad / total : 0;
        
        String estadisticas = String.format(
            "📊 ESTADÍSTICAS GENERALES\n" +
            "══════════════════════════\n\n" +
            "🚗 TOTAL DE VEHÍCULOS: %d\n\n" +
            "📈 POR ESTADO:\n" +
            "   • Disponibles: %d (%.1f%%)\n" +
            "   • En alquiler: %d (%.1f%%)\n" +
            "   • En mantenimiento: %d (%.1f%%)\n\n" +
            "🚙 POR TIPO:\n" +
            "   • Sedanes: %d\n" +
            "   • SUVs: %d\n" +
            "   • Pick-ups: %d\n\n" +
            "🕐 POR ANTIGÜEDAD:\n" +
            "   • Nuevos (≤5 años): %d\n" +
            "   • Antiguos (>5 años): %d\n" +
            "   • Promedio: %.1f años",
            total,
            disponibles, (total > 0 ? (disponibles * 100.0 / total) : 0),
            alquilados, (total > 0 ? (alquilados * 100.0 / total) : 0),
            enMantenimiento, (total > 0 ? (enMantenimiento * 100.0 / total) : 0),
            sedanes, suvs, pickups,
            nuevos, viejos, promedioAntiguedad
        );
        
        JOptionPane.showMessageDialog(this, estadisticas, 
            "Estadísticas del Sistema", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // NUEVO: Menú contextual (click derecho)
    private void mostrarMenuContextual(java.awt.event.MouseEvent evt) {
        JPopupMenu menu = new JPopupMenu();
        
        JMenuItem detalles = new JMenuItem("🔍 Ver Detalles Completos");
        detalles.addActionListener(e -> mostrarDetallesCompletos());
        
        JMenuItem disponibles = new JMenuItem("✅ Solo Disponibles");
        disponibles.addActionListener(e -> mostrarSoloDisponibles());
        
        JMenuItem alquilados = new JMenuItem("⚠️ Solo En Alquiler");
        alquilados.addActionListener(e -> mostrarSoloAlquilados());
        
        JMenuItem nuevos = new JMenuItem("✨ Solo Nuevos");
        nuevos.addActionListener(e -> mostrarVehiculosNuevos());
        
        JMenuItem porTipo = new JMenuItem("🚗 Filtrar por Tipo");
        porTipo.addActionListener(e -> filtrarPorTipo());
        
        JMenuItem porAntiguedad = new JMenuItem("🕐 Filtrar por Antigüedad");
        porAntiguedad.addActionListener(e -> filtrarPorAntiguedad());
        
        JMenuItem estadisticas = new JMenuItem("📊 Ver Estadísticas");
        estadisticas.addActionListener(e -> mostrarEstadisticas());
        
        JMenuItem mostrarTodos = new JMenuItem("🔄 Mostrar Todos");
        mostrarTodos.addActionListener(e -> cargarDatosTabla());
        
        menu.add(detalles);
        menu.addSeparator();
        menu.add(disponibles);
        menu.add(alquilados);
        menu.add(nuevos);
        menu.addSeparator();
        menu.add(porTipo);
        menu.add(porAntiguedad);
        menu.addSeparator();
        menu.add(estadisticas);
        menu.add(mostrarTodos);
        
        menu.show(tablaVehiculos, evt.getX(), evt.getY());
    }
    
   
    
    private TipoVehiculo obtenerTipoDeComboBox() {
        String descripcion = (String) cmbTipo.getSelectedItem();
        for (TipoVehiculo tipo : TipoVehiculo.values()) {
            if (tipo.getDescripcion().equals(descripcion)) {
                return tipo;
            }
        }
        return TipoVehiculo.SEDAN;
    }
    
    private EstadoVehiculo obtenerEstadoDeComboBox() {
        String descripcion = (String) cmbEstado.getSelectedItem();
        for (EstadoVehiculo estado : EstadoVehiculo.values()) {
            if (estado.getDescripcion().equals(descripcion)) {
                return estado;
            }
        }
        return EstadoVehiculo.DISPONIBLE;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVehiculos = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnOpciones = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtPlaca = new javax.swing.JTextField();
        txtMarca = new javax.swing.JTextField();
        txtModelo = new javax.swing.JTextField();
        cmbTipo = new javax.swing.JComboBox<>();
        cmbEstado = new javax.swing.JComboBox<>();
        txtAno = new javax.swing.JTextField();

        setBackground(new java.awt.Color(102, 102, 102));
        setClosable(true);
        setForeground(new java.awt.Color(0, 0, 102));
        setMaximizable(true);
        setResizable(true);
        setOpaque(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Gestionar Vehiculo");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, 184, 33));

        jInternalFrame1.setClosable(true);
        jInternalFrame1.setMaximizable(true);
        jInternalFrame1.setResizable(true);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Gestionar Vehiculo");

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jInternalFrame1, new org.netbeans.lib.awtextra.AbsoluteConstraints(362, 273, 0, 0));

        tablaVehiculos.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 153), new java.awt.Color(0, 0, 204), null, new java.awt.Color(0, 0, 0)));
        tablaVehiculos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Placa", "Marca", "Modelo", "Año ", "Tipo", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaVehiculos.setGridColor(new java.awt.Color(204, 204, 204));
        tablaVehiculos.setRowHeight(25);
        tablaVehiculos.setSelectionBackground(new java.awt.Color(0, 0, 204));
        tablaVehiculos.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(tablaVehiculos);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 39, 444, 302));

        jSeparator1.setForeground(new java.awt.Color(153, 153, 153));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 347, 712, 29));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btnActualizar.setBackground(new java.awt.Color(0, 51, 255));
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("Actualizar");
        btnActualizar.setBorderPainted(false);
        btnActualizar.setFocusPainted(false);
        btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnActualizarMouseEntered(evt);
            }
        });
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(204, 0, 0));
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorderPainted(false);
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnAgregar.setBackground(new java.awt.Color(0, 255, 0));
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("Agregar");
        btnAgregar.setBorderPainted(false);
        btnAgregar.setFocusPainted(false);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(153, 153, 153));
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.setBorderPainted(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnOpciones.setBackground(new java.awt.Color(255, 153, 0));
        btnOpciones.setForeground(new java.awt.Color(255, 255, 255));
        btnOpciones.setText("Opciones");
        btnOpciones.setBorderPainted(false);
        btnOpciones.setFocusPainted(false);
        btnOpciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpcionesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnOpciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(485, 51, -1, 290));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 153));
        jLabel3.setText("Placa:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 153));
        jLabel4.setText("Marca:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 153));
        jLabel6.setText("Modelo:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 153));
        jLabel7.setText("Tipo:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 153));
        jLabel8.setText("Estado:");

        jLabel9.setForeground(new java.awt.Color(0, 0, 153));
        jLabel9.setText(" Año:");

        txtPlaca.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtMarca.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMarcaActionPerformed(evt);
            }
        });

        txtModelo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        cmbTipo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        cmbEstado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtAno.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPlaca, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                            .addComponent(txtMarca)
                            .addComponent(txtModelo))
                        .addGap(148, 148, 148)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(143, 143, 143))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 382, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMarcaActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
         int filaSeleccionada = tablaVehiculos.getSelectedRow();
        if (filaSeleccionada < 0) {
            UtilGui.showErrorMessage(this, "Seleccione un vehículo para actualizar", "Advertencia");
            return;
        }
        
        try {
            if (validarCampos()) {
                
                TipoVehiculo tipoSeleccionado = null;
                String tipoString = (String) cmbTipo.getSelectedItem();
                for (TipoVehiculo tipo : TipoVehiculo.values()) {
                    if (tipo.getDescripcion().equals(tipoString)) {
                        tipoSeleccionado = tipo;
                        break;
                    }
                }
                
                EstadoVehiculo estadoSeleccionado = null;
                String estadoString = (String) cmbEstado.getSelectedItem();
                for (EstadoVehiculo estado : EstadoVehiculo.values()) {
                    if (estado.getDescripcion().equals(estadoString)) {
                        estadoSeleccionado = estado;
                        break;
                    }
                }
                
                Vehiculo actualizado = new Vehiculo(
                    txtPlaca.getText().trim(),
                    txtMarca.getText().trim(),
                    txtModelo.getText().trim(),
                    Integer.parseInt(txtAno.getText().trim()),
                    tipoSeleccionado
                );
                actualizado.setEstado(estadoSeleccionado);
                
                if (listaVehiculos.actualizar(actualizado)) {
                   UtilGui.showMessage(this, 
                    "Vehículo actualizado exitosamente\n" + actualizado.toString(),
                    "Operación Exitosa");
                    cargarDatosTabla();
                    limpiarFormulario();
                } else {
                     UtilGui.showErrorMessage(this, "Error al actualizar vehículo", "Error");
                }
            }
        } catch (NumberFormatException e) {
           UtilGui.showErrorMessage(this, "El año debe ser un número válido", "Error de Formato");
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
      int filaSeleccionada = tablaVehiculos.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un vehículo para eliminar");
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este vehículo?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirmacion == JOptionPane.YES_OPTION) {
            DefaultTableModel modelo = (DefaultTableModel) tablaVehiculos.getModel();
            String placa = (String) modelo.getValueAt(filaSeleccionada, 0);
            Vehiculo vehiculo = listaVehiculos.buscar(placa);
            
            try {
                if (listaVehiculos.eliminar(vehiculo)) {
                    JOptionPane.showMessageDialog(this, "Vehículo eliminado exitosamente");
                    cargarDatosTabla();
                    limpiarFormulario();
                }
            } catch (IllegalStateException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        try {
            if (validarCampos()) {
              
                TipoVehiculo tipoSeleccionado = null;
                String tipoString = (String) cmbTipo.getSelectedItem();
                for (TipoVehiculo tipo : TipoVehiculo.values()) {
                    if (tipo.getDescripcion().equals(tipoString)) {
                        tipoSeleccionado = tipo;
                        break;
                    }
                }
                
                Vehiculo nuevo = new Vehiculo(
                    txtPlaca.getText().trim(),
                    txtMarca.getText().trim(),
                    txtModelo.getText().trim(),
                    Integer.parseInt(txtAno.getText().trim()),
                    tipoSeleccionado
                );
                
                if (listaVehiculos.agregar(nuevo)) {
                    UtilGui.showMessage(this, 
                    "Vehículo agregado exitosamente\n" + nuevo.toString(), 
                    "Operación Exitosa");
                    cargarDatosTabla();
                    limpiarFormulario();
                } else {
                      UtilGui.showErrorMessage(this, 
                    "No se pudo agregar el vehículo.\nVerifique que la placa no esté duplicada.", 
                    "Error al Agregar");
                }
            }
        } catch (NumberFormatException e) {
            UtilGui.showErrorMessage(this, "El año debe ser un número válido", "Error de Formato");
        }
    
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String placa = JOptionPane.showInputDialog(this, "Ingrese la placa del vehículo:");
        if (placa != null && !placa.trim().isEmpty()) {
            Vehiculo vehiculo = listaVehiculos.buscar(placa.trim());
            if (vehiculo != null) {
               
                DefaultTableModel modelo = (DefaultTableModel) tablaVehiculos.getModel();
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    if (modelo.getValueAt(i, 0).equals(placa.trim())) {
                        tablaVehiculos.setRowSelectionInterval(i, i);
                        cargarDatosFormulario();
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vehículo no encontrado");
            }
        }
    
    
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnOpcionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpcionesActionPerformed
      String[] opciones = {
        "🔍 Ver Detalles Completos",
        "✅ Mostrar Solo Disponibles", 
        "⚠️ Mostrar Solo Alquilados",
        "🔄 Mostrar Todos",
        "📊 Ver Estadísticas",
        "🕐 Filtrar por Antigüedad",
        "🚗 Filtrar por Tipo"
    };
    
    String seleccion = (String) JOptionPane.showInputDialog(
        this,
        "Seleccione una opción:",
        "🛠️ Opciones Avanzadas",
        JOptionPane.QUESTION_MESSAGE,
        null,
        opciones,
        opciones[0]
    );
    
    if (seleccion != null) {
        switch (seleccion) {
            case "🔍 Ver Detalles Completos":
                mostrarDetallesCompletos();
                break;
            case "✅ Mostrar Solo Disponibles":
                mostrarSoloDisponibles();
                break;
            case "⚠️ Mostrar Solo Alquilados":
                mostrarSoloAlquilados();
                break;
            case "🔄 Mostrar Todos":
                cargarDatosTabla();
                JOptionPane.showMessageDialog(this, "Mostrando todos los vehículos");
                break;
            case "📊 Ver Estadísticas":
                mostrarEstadisticas();
                break;
            case "🕐 Filtrar por Antigüedad":
                filtrarPorAntiguedad();
                break;
            case "🚗 Filtrar por Tipo":
                filtrarPorTipo();
                break;
        }
    }
    }//GEN-LAST:event_btnOpcionesActionPerformed

    private void btnActualizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnActualizarMouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnOpciones;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tablaVehiculos;
    private javax.swing.JTextField txtAno;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtPlaca;
    // End of variables declaration//GEN-END:variables

    
}
