package equipoideal.view.dialogs;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import equipoideal.model.listener.IncompatiblesListener;
import equipoideal.view.components.DialogStyleHelper; 

public class IncompatibleDialog extends DialogPadre {
    
	private static final long serialVersionUID = 1L;
	private JComboBox<String> selectorPersona1;
    private JComboBox<String> selectorPersona2;
    private JButton btnEliminar;
    private IncompatiblesListener listener;


    public IncompatibleDialog(String titulo) {
        super(titulo);
        
    }

    public void setIncompatiblesListener(IncompatiblesListener listener) {
        this.listener = listener;
    }

    @Override
    public void crearInputs() {
    	btnAceptar.setText("Registrar Incompatibilidad");
    	
    	btnEliminar = crearBoton("Eliminar Incompatibilidad");
        grillaBotones.add(btnEliminar);

        panelSuperior.setLayout(new GridLayout(2, 2, 10, 10));
        JPanel panelSeleccion = crearPanel(new GridLayout(2,1, 10, 10));
    	
        JLabel lblPersona1 = crearLabel("Persona 1:", 12);
        JLabel lblPersona2 = crearLabel("Persona 2:", 12);
        
        selectorPersona1 = new JComboBox<>();
        selectorPersona2 = new JComboBox<>();
        
        
        panelSeleccion.add(lblPersona1);
        panelSeleccion.add(lblPersona2);
        panelSeleccion.add(selectorPersona1);
        panelSeleccion.add(selectorPersona2);
       
        panelSuperior.add(panelSeleccion);
        
        String[] columnas = {"Persona1", "Persona2"};
		configurarTabla(columnas, panelCentral);
        configurarColumnasTabla();
        
        JLabel lblTituloTabla = crearLabel("Personas Incompatibles", 12);
        lblTituloTabla.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloTabla.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        lblTituloTabla.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 0, 5, 0));
        
        lblTituloTabla.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        	    javax.swing.BorderFactory.createEmptyBorder(30, 0, 5, 0),
        	    javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, DialogStyleHelper.ColorBorde)));
        
        JPanel contenedorNorte = new JPanel();
        contenedorNorte.setOpaque(false);
        contenedorNorte.setLayout(new javax.swing.BoxLayout(contenedorNorte, javax.swing.BoxLayout.Y_AXIS));
        
        contenedorNorte.add(panelBotonesMedio);
        contenedorNorte.add(lblTituloTabla);
        
        //contenedor completo al NORTH del panel central
        panelCentral.add(contenedorNorte, java.awt.BorderLayout.NORTH);
    }
    
    private void configurarColumnasTabla() {
      tabla.setTableHeader(null); 
    }
    
    public int getFilaSeleccionada() {
        return tabla.getSelectedRow(); // Devuelve -1 si no tocó ninguna fila
    }

    public String getNombrePersona1DeTabla(int fila) {
        return tabla.getValueAt(fila, 0).toString();
    }

    public String getNombrePersona2DeTabla(int fila) {
        return tabla.getValueAt(fila, 1).toString();
    }

    public void eliminarFilaTabla(int fila) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.removeRow(fila);
    }

    public void cargarPersonasEnSelectores(List<String> nombresPersonas) {
    	selectorPersona1.removeAllItems();
    	selectorPersona2.removeAllItems();
        for (String nombre : nombresPersonas) {
        	selectorPersona1.addItem(nombre);
        	selectorPersona2.addItem(nombre);
        }
    }

    public int getIndexPersona1() { 
    	return selectorPersona1.getSelectedIndex();
    	}
    public int getIndexPersona2() {
    	return selectorPersona2.getSelectedIndex();
    	}

    public void agregarIncompatibilidadTabla(String p1, String p2) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.addRow(new Object[]{p1, p2});
    }

    @Override
    public void accionesBoton() {
        btnAceptar.addActionListener(e -> {
            if (listener != null) listener.onIncompatibilidadRegistrada();
        }); 
        
        btnEliminar.addActionListener(e -> {
            if (listener != null) listener.onIncompatibilidadEliminada();
        });
    }

    @Override
    public void limpiarInputs() {
        if (selectorPersona1.getItemCount() > 0) selectorPersona1.setSelectedIndex(0);
        if (selectorPersona2.getItemCount() > 0) selectorPersona2.setSelectedIndex(0);
    }
    
    public JButton getBtnAceptar() {
        return btnAceptar; 
    }
    
    public boolean tieneIncompatibilidadesCargadas() {
        if (tabla == null || tabla.getModel() == null) {
            return false;
        }
        return tabla.getModel().getRowCount() > 0;
    }
    
}