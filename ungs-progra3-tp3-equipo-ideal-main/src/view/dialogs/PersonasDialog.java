package view.dialogs;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import view.dialog.event.PersonasListener;


public class PersonasDialog extends DialogPadre{
	private JButton btnCargarDesde;
	private TextField txtNombre;
	private TextField txtApellido;
	private TextField txtPuntuacion;
	private TextField txtRol;
	private JButton btnFoto;
	private JPanel panelBotonesNuevos;
	private PersonasListener listener;
	
	


	public PersonasDialog(JFrame frame, String titulo) {
		super(frame, titulo);
		
	}
	
	public void setPersonasListener(PersonasListener listener) {
		this.listener = listener;
	}

	@Override
	public void crearInputs() {
		
		panelInputs.setLayout(new BorderLayout(0, 10));
		
		
		btnCargarDesde = new JButton("Cargar Personas Desde...");
		
		panelBotonesNuevos = new JPanel(new GridLayout(1, 1, 100, 2));
	    panelBotonesNuevos.setOpaque(false);
	    
	    btnFoto = new JButton("Cargar Foto"); 
	    
	    panelBotonesNuevos.add(btnFoto, BorderLayout.WEST);
	    panelBotonesNuevos.add(btnCargarDesde, BorderLayout.EAST);
	    
	    JPanel nuevosInputs = new JPanel(new GridLayout(4, 2, 10, 10)); 
	    nuevosInputs.setOpaque(false);
		
		JLabel lblNombre = new JLabel("Ingrese Nombre:");
		txtNombre = new TextField();
		
		JLabel lblApellido = new JLabel("Ingrese Apellido:");
		txtApellido = new TextField();
		
		JLabel lblPuntuacion = new JLabel("Ingrese Puntos:");
		txtPuntuacion = new TextField();
		
		JLabel lblRol = new JLabel("Ingrese Puesto:");
		txtRol = new TextField();
		
		nuevosInputs.add(lblNombre);
		nuevosInputs.add(lblApellido);
		
		nuevosInputs.add(txtNombre);
		nuevosInputs.add(txtApellido);
		
		nuevosInputs.add(lblPuntuacion);
		nuevosInputs.add(lblRol);
		
		nuevosInputs.add(txtPuntuacion);
		nuevosInputs.add(txtRol);
		
		panelInputs.add(panelBotonesNuevos, BorderLayout.NORTH);
		panelInputs.add(nuevosInputs, BorderLayout.CENTER);
		

		panelBotonesNuevos.setBorder(BorderFactory.createEmptyBorder(10,40,20,40));
		
		String[] columnas = {"Nombre", "Apellido", "Puesto", "Puntos"};
        DefaultTableModel modeloVacio = new DefaultTableModel(columnas, 0);

        tabla = new JTable(modeloVacio);
        scrollPane = new JScrollPane(tabla);
        
        panelLista.setLayout(new BorderLayout());
        panelLista.add(scrollPane, BorderLayout.CENTER);
        panelLista.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
		
        accionesBoton();
		
	}
	
	@Override
	public void accionesBoton() {
		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre = txtNombre.getText();
				String apellido = txtApellido.getText();
				int puntos = Integer.parseInt(txtPuntuacion.getText());
				String puesto = txtRol.getText();
				
				
				if (listener != null) {
					listener.onPersonaAgregada(nombre, apellido, puntos, puesto);
				}
			}
		});
		
	}
	
	public void actualizarTablaPersonas(Object[][] datosTabla) {
	   
	    DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
	  
	    if (datosTabla != null) {
	        for (Object[] fila : datosTabla) {
	            modelo.addRow(fila);
	        }
	    }
	    limpiarInputs();
	}
	
	public void limpiarInputs() {
	    txtNombre.setText("");
	    txtApellido.setText("");
	    txtPuntuacion.setText("");
	    txtRol.setText("");
	    txtNombre.requestFocus();
	}
	

}
