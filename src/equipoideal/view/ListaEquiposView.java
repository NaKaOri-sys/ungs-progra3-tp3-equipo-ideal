package equipoideal.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import equipoideal.model.listener.ResultadosListener;

public class ListaEquiposView extends JPanel {
    
    private JList<String> listaEquipos;
    private DefaultListModel<String> datosEquipos;  
    private JButton btnVerDetalle;
    private JButton btnElegirSeleccionada;
    private ResultadosListener listener; 

    public ListaEquiposView() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        datosEquipos = new DefaultListModel<>();
        listaEquipos = new JList<>(datosEquipos);
        listaEquipos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //deberia prevenir seleccionar varios esquipos a la vez
        JScrollPane scrollPane = new JScrollPane(listaEquipos);
        add(scrollPane, BorderLayout.CENTER);
        
//        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 0, 15));
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 0, 15));
        panelBotones.setPreferredSize(new Dimension(180, 100));
        
        btnVerDetalle = new JButton("Ver Detalle");
        btnElegirSeleccionada = new JButton("Elegir Seleccionada");
        
        panelBotones.add(new JLabel(""));
        panelBotones.add(btnVerDetalle);
        panelBotones.add(new JLabel(""));
        panelBotones.add(btnElegirSeleccionada);
        panelBotones.add(new JLabel(""));
        add(panelBotones, BorderLayout.EAST);
        
        configurarAcciones();
    }
    
    public void setResultadosListener(ResultadosListener listener) {
        this.listener = listener;
    }
    
    private void configurarAcciones() {
        btnVerDetalle.addActionListener(e -> {
            int index = listaEquipos.getSelectedIndex();
            if (index != -1 && listener != null) {
            	listener.onVerDetalle(index); //
            }
        });
        
        btnElegirSeleccionada.addActionListener(e -> {
            int index = listaEquipos.getSelectedIndex();
            if (index != -1 && listener != null) {
                listener.onElegirSeleccionada(index); 
            }
        });
    }
    
    public void cargarListaEquipos(String[] nombresEquipos) {
    	datosEquipos.clear();
        if (nombresEquipos != null) {
            for (String equipo : nombresEquipos) {
            	datosEquipos.addElement(equipo);
            }
        }
    }  
      
}