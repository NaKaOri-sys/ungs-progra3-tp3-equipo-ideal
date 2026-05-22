package equipoideal.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import equipoideal.model.listener.RequerimientosListener;


public class RequerimientosDialog extends DialogPadre{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSpinner spinnerLider;
	private JSpinner spinnerArquitecto;
	private JSpinner spinnerProgramador;
	private JSpinner spinnerTester;
	private RequerimientosListener listener;

	public RequerimientosDialog(JFrame frame, String titulo) {
		super(frame, titulo);
	}
	
	public void setRequerimientosListener(RequerimientosListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void crearInputs() {
		
		panelInputs.setLayout(new GridLayout(4, 1, 10, 10));
		spinnerLider = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
		spinnerArquitecto = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
		spinnerProgramador = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
		spinnerTester = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
		
		panelInputs.add(crearFila("Lider De Equipo", spinnerLider));
		panelInputs.add(crearFila("Arquitecto", spinnerArquitecto));
		panelInputs.add(crearFila("Programador", spinnerProgramador));
        panelInputs.add(crearFila("Testers", spinnerTester));
        
        accionesBoton();
        

		panelInputs.setBorder(BorderFactory.createEmptyBorder(20,200,20,200));
        
        String[] columnas = {"Nombre", "Apellido", "Puesto", "Puntos"};
        DefaultTableModel modeloVacio = new DefaultTableModel(columnas, 0);

        tabla = new JTable(modeloVacio);
        scrollPane = new JScrollPane(tabla);
        
        panelLista.setLayout(new BorderLayout());
        panelLista.add(scrollPane, BorderLayout.CENTER);
        panelLista.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
    }
		
	private JPanel crearFila(String nombre, JSpinner spinnerFila) {
		JPanel fila = new JPanel();
		fila.setLayout(new BorderLayout());
		fila.setBackground(ColorFondo);
		
		JLabel lblFila = new JLabel(nombre);
		lblFila.setPreferredSize(new Dimension(140, 30));
		
		fila.add(lblFila, BorderLayout.WEST);
		fila.add(spinnerFila, BorderLayout.EAST);
		
		return fila;
	}
	
	//TODO aprovechar el listener
	@Override
	public void accionesBoton() {
		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int lideres = (int) spinnerLider.getValue();
				int arquitectos = (int) spinnerArquitecto.getValue();
				int programadores = (int) spinnerProgramador.getValue();
				int testers = (int) spinnerTester.getValue();
				
				
				if (listener != null) {
					
					listener.onRequerimientosAgregados(lideres, arquitectos, programadores, testers);
				}
			}
		});
	}
		
		
		
	
		
	
}
