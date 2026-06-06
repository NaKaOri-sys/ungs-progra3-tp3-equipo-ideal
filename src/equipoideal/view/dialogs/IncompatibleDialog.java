package equipoideal.view.dialogs;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import equipoideal.model.listener.IncompatiblesListener; 

public class IncompatibleDialog extends DialogPadre {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<String> selectorPersona1;
    private JComboBox<String> selectorPersona2;
    private IncompatiblesListener listener;

    public IncompatibleDialog(String titulo) {
        super(titulo);
        crearInputs();
        accionesBoton();
    }

    public void setIncompatiblesListener(IncompatiblesListener listener) {
        this.listener = listener;
    }

    @Override
    public void crearInputs() {
        panelInputs.setLayout(new GridLayout(2, 2, 10, 10));
        
        JLabel lblPersona1 = new JLabel("Persona 1:");
        JLabel lblPersona2 = new JLabel("Persona 2:");
        
        selectorPersona1 = new JComboBox<>();
        selectorPersona2 = new JComboBox<>();
        
        panelInputs.add(lblPersona1);
        panelInputs.add(lblPersona2);
        panelInputs.add(selectorPersona1);
        panelInputs.add(selectorPersona2);
        
        panelBotones.removeAll(); 
       
        btnAceptar.setText("Registrar Incompatibilidad"); 
        
        JPanel filaSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filaSuperior.setOpaque(false);
        filaSuperior.add(btnAceptar);
        
        JPanel filaInferior = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        filaInferior.setOpaque(false);
        filaInferior.add(btnCerrar);
        
        panelBotones.setLayout(new GridLayout(2, 1, 0, 5));
        panelBotones.add(filaSuperior);
        panelBotones.add(filaInferior);

        crearTabla();
        configurarColumnasTabla();
    }

    private void configurarColumnasTabla() {
        String[] columnasIncompatibles = {"Persona 1", "Persona 2"};
        DefaultTableModel modeloCustom = new DefaultTableModel(columnasIncompatibles, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla.setModel(modeloCustom);
    }

    public void cargarPersonasEnSelectores(List<String> nombresPersonas) {
    	selectorPersona1.removeAllItems();
    	selectorPersona2.removeAllItems();
        for (String nombre : nombresPersonas) {
        	selectorPersona1.addItem(nombre);
        	selectorPersona2.addItem(nombre);
        }
    }

    public int getIndexPersona1() { return selectorPersona1.getSelectedIndex(); }
    public int getIndexPersona2() { return selectorPersona2.getSelectedIndex(); }

    public void agregarIncompatibilidadTabla(String p1, String p2) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.addRow(new Object[]{p1, p2});
    }

    @Override
    public void accionesBoton() {
        btnAceptar.addActionListener(e -> {
            if (listener != null) listener.onIncompatibilidadRegistrada();
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
}