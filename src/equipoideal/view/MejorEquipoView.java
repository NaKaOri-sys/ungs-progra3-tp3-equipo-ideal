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

import equipoideal.model.Navigation;
import equipoideal.model.listener.ResultadosListener;
import equipoideal.util.VentanaEnum;

public class MejorEquipoView extends JPanel {

    
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JButton btnVolverMenu;
    private JButton btnCerrarApp;
    private ResultadosListener listener;

    public MejorEquipoView() {
        
        
        setLayout(new BorderLayout(20, 20)); // 20 de espacio horizontal y vertical
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen externo
        
       
        String[] columnas = {"Nombre", "Apellido", "Puesto", "Puntos"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaResultados = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        
        
        add(scrollPane, BorderLayout.CENTER);
        
       
//        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 0, 15)); // 2 filas, 1 columna, hace los botones mas grandes
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 0, 15)); //  5 filas y 1 sola columnas
        
        btnVolverMenu = new JButton("Volver al Menú");
        btnCerrarApp = new JButton("Cerrar Aplicación");
        
        //tamaño panel de botones para que no se deforme
        panelBotones.setPreferredSize(new Dimension(180, 100));
        
        panelBotones.add(new JLabel(""));
        panelBotones.add(btnVolverMenu);
        panelBotones.add(new JLabel(""));
        panelBotones.add(btnCerrarApp);
        panelBotones.add(new JLabel(""));
        
        //  el panel de botones a la derecha
        add(panelBotones, BorderLayout.EAST);
        
        botonesAcciones();    
    }
    
    private void botonesAcciones() {
        
        btnCerrarApp.addActionListener(e -> System.exit(0));
        

        btnVolverMenu.addActionListener(e -> {                        //falta esto, no se a donde deberia volver, si uno atras o reiniciar todo
            if (listener != null) {

            }
        });
    }
    
    // deberia usarlo nahuel para cargar mi tabla
    public void cargarEquipoEnTabla(Object[][] datosEquipo) {
        modeloTabla.setRowCount(0); // Limpia la tabla
        if (datosEquipo != null) {
            for (Object[] fila : datosEquipo) {
                modeloTabla.addRow(fila);
            }
        }
    }
    
}