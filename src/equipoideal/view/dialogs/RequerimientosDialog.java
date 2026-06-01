package equipoideal.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import equipoideal.model.dto.PersonaDto;
import equipoideal.model.dto.RequerimientosDto;
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

	public RequerimientosDialog(JDialog frame, String titulo) {
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
		
		crearTabla();
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
	
	
	@Override
	public void accionesBoton() {
		btnAceptar.addActionListener(e -> {
			if (listener != null) {
				listener.onRequerimientosAgregados();
			}
		});
	}
		
	public RequerimientosDto getRequerimientos() {
	    return new RequerimientosDto(
	        (int) spinnerLider.getValue(),
	        (int) spinnerArquitecto.getValue(),
	        (int) spinnerProgramador.getValue(),
	        (int) spinnerTester.getValue());
	}

	public void actualizarTablaPersonas(ArrayList<PersonaDto> personas) {

	    DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

	    modelo.setRowCount(0);

	    for (PersonaDto p : personas) {

	        modelo.addRow(new String[] {
	            p.getNombre(),
	            p.getApellido(),
	            p.getRol(),
	            String.valueOf(p.getCalificacion())
	        });
	    }
	}

	public void limpiarInputs() {

	    spinnerLider.setValue(0);

	    spinnerArquitecto.setValue(0);

	    spinnerProgramador.setValue(0);

	    spinnerTester.setValue(0);
	}
		
	
		
	
}
