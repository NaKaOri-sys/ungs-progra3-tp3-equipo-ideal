package equipoideal.view.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class IncompatibleDialog extends DialogPadre{
	private JButton btnCargarDesde;

	public IncompatibleDialog(JFrame frame, String titulo) {
		super(frame, titulo);
	}

	@Override
	public void crearInputs() {
		panelInputs.setLayout(new GridLayout(2, 2, 10, 10));
		JLabel lblPersona1 = new JLabel("Persona 1");
		
		JLabel lblPersona2 = new JLabel("Persona 2");
		
		JComboBox<String> boxPersona1 = new JComboBox<>();
		JComboBox<String> boxPersona2 = new JComboBox<>();  //Cambiar
		
		boxPersona1.addItem("hola");
		boxPersona1.addItem("hola1");
		boxPersona2.addItem("chau");    //Son solo para probar
		boxPersona2.addItem("chau1");
		
		panelInputs.add(lblPersona1);
		panelInputs.add(lblPersona2);
		
		panelInputs.add(boxPersona1);
		panelInputs.add(boxPersona2);
		
		panelBotones.removeAll();

		
		btnCargarDesde = new JButton("Cargar Desde...");
        
		
		JPanel filaSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    filaSuperior.setOpaque(false);
	    filaSuperior.add(btnAceptar);
		

	    JPanel filaInferior = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
	    filaInferior.setOpaque(false);

		filaInferior.add(btnCargarDesde, BorderLayout.WEST);

		filaInferior.add( btnCerrar, BorderLayout.EAST);
		
		panelBotones.add(filaSuperior);

		panelBotones.add(filaInferior);

		
		String[] columnas = {"Persona 1", "Persona 2", "Estado/Compatibilidad"};
        DefaultTableModel modeloVacio = new DefaultTableModel(columnas, 0);

        tabla = new JTable(modeloVacio);
        scrollPane = new JScrollPane(tabla);
        
        panelLista.setLayout(new BorderLayout());
        panelLista.add(scrollPane, BorderLayout.CENTER);
		
		
	}

	@Override
	public void accionesBoton() {
		
	}

}
