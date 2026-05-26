package equipoideal.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import equipoideal.model.listener.ResultadosListener;

public class DetalleEquipoView extends JPanel {

    
    private JTable tablaIntegrantes;
    private DefaultTableModel Tabla;
    private JButton btnVerEmpleado;
    private JButton btnCerrar;
    private ResultadosListener listener;

    public DetalleEquipoView() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        String[] columnas = {"Nombre", "Apellido", "Puesto", "Puntos"};
        Tabla = new DefaultTableModel(columnas, 0);
        tablaIntegrantes = new JTable(Tabla);
        JScrollPane scrollPane = new JScrollPane(tablaIntegrantes);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 0, 15)); //  5 filas y 1 sola columnas
        panelBotones.setPreferredSize(new Dimension(180, 100));
        
        
        
        btnVerEmpleado = new JButton("Ver Empleado");                     
        btnCerrar = new JButton("Volver");
        
        panelBotones.add(new JLabel("")); 
        panelBotones.add(btnVerEmpleado);
        panelBotones.add(new JLabel("")); 
        panelBotones.add(btnCerrar);
        panelBotones.add(new JLabel("")); 
        add(panelBotones, BorderLayout.EAST);
        
        configurarBotones();
    }

    public void setResultadosListener(ResultadosListener listener) {
        this.listener = listener;
    }
    
    private void configurarBotones() {
    	// Cuando hacen clic en Volver, cambiar nombre
        btnCerrar.addActionListener(e -> {
            if (listener != null) {
                listener.onCerrarDetalle();
            }
        });
        
        btnVerEmpleado.addActionListener(e -> {
            int filaSeleccionada = tablaIntegrantes.getSelectedRow();
            if (filaSeleccionada != -1 && listener != null) {
                listener.onVerEmpleado(filaSeleccionada);
            }
        });
    }
    
    public void cargarIntegrantes(Object[][] datosIntegrantes) {
        Tabla.setRowCount(0);     //limpia la tabla (por si queres ver distintas)
        if (datosIntegrantes != null) {
            for (Object[] fila : datosIntegrantes) {
                Tabla.addRow(fila);
            }
        }
    }
    
    public javax.swing.JTable getTablaIntegrantes() {                        //sacar
        return this.tablaIntegrantes;
    }
}

