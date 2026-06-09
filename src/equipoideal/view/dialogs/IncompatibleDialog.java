package equipoideal.view.dialogs;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import equipoideal.model.Persona;
import equipoideal.model.dto.PersonaDto;
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
    	
    	btnEliminar = crearBoton("Eliminar Incompatibilidad Seleccionada");
    	
    	panelBotonesMedio.setLayout(new GridLayout(2, 1, 10, 10));
    	panelBotonesMedio.setBorder(BorderFactory.createEmptyBorder(10, 200, 0, 200));
    	JPanel panelFormulario = crearPanelConBordeEspecial(new BorderLayout(), DialogStyleHelper.ColorFondoPrincipal);
    	
        panelSuperior.setLayout(new GridLayout(2, 2, 10, 10));
        JPanel panelSeleccion = crearPanel(new GridLayout(2,1, 10, 10));
    	
        JLabel lblPersona1 = crearLabel("Persona:", 12);
        JLabel lblPersona2 = crearLabel("Persona Incompatible:", 12);
        
        selectorPersona1 = new JComboBox<>();
        selectorPersona2 = new JComboBox<>();
        
        
        panelSeleccion.add(lblPersona1);
        panelSeleccion.add(lblPersona2);
        panelSeleccion.add(selectorPersona1);
        panelSeleccion.add(selectorPersona2);
       
        panelSuperior.add(panelSeleccion);
        
        String[] columnas = {"Persona", "Conector", "Persona"};
		configurarTabla(columnas, panelCentral);
        configurarColumnasTabla();
        
        JLabel lblTituloTabla = crearLabel("Personas Incompatibles", 12);
        lblTituloTabla.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        //Subrayado
        lblTituloTabla.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createEmptyBorder(0, 0, 5, 0), 
                javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, DialogStyleHelper.ColorBorde)));
        
        panelFormulario.add(btnEliminar, BorderLayout.NORTH);
        panelFormulario.add(lblTituloTabla, BorderLayout.SOUTH);
        
        panelBotonesMedio.add(panelFormulario);
    }
    
    private void configurarColumnasTabla() {
      tabla.setTableHeader(null); 
    }
    
    public int getFilaSeleccionada() {
        return tabla.getSelectedRow(); // Devuelve -1 si no tocó ninguna fila
    }
    
    public Persona getPersona1DeTabla(int fila) {
        return (Persona) tabla.getValueAt(fila, 0);
    }
    
    public Persona getPersona2DeTabla(int fila) {
        return (Persona) tabla.getValueAt(fila, 2);
    }
    
    public void eliminarFilaTabla(int fila) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.removeRow(fila);
    }

    public void cargarPersonasEnSelectores(List<PersonaDto> personas) {
    	selectorPersona1.removeAllItems();
    	selectorPersona2.removeAllItems();
        for (PersonaDto p : personas) {
        	selectorPersona1.addItem(p.toString());
        	selectorPersona2.addItem(p.toString());
        }
    }

    public int getIndexPersona1() { 
    	return selectorPersona1.getSelectedIndex();
    	}
    public int getIndexPersona2() {
    	return selectorPersona2.getSelectedIndex();
    	}

    public void agregarIncompatibilidadTabla(Persona p1, Persona p2) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.addRow(new Object[]{p1, "con", p2});
    }

    @Override
    public void accionesBoton() {
        btnAceptar.addActionListener(e -> {
            if (listener != null) listener.alRegistrarIncompatibilidad();
        }); 
        
        btnEliminar.addActionListener(e -> {
            if (listener != null) listener.alBorrarIncompatibilidad();
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