package equipoideal.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import equipoideal.model.dto.PersonaDto;
import equipoideal.model.dto.RequerimientoDto;
import equipoideal.model.listener.RequerimientoListener;

public class RequerimientoDialog extends DialogPadre {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSpinner spinnerLider;
	private JSpinner spinnerArquitecto;
	private JSpinner spinnerProgramador;
	private JSpinner spinnerTester;
	private RequerimientoListener listener;

	private JTable tablaRequerimientos;
	private DefaultTableModel modeloTRequerimientos;

	public RequerimientoDialog(String titulo) {
		super(titulo);
	}

	public void setRequerimientosListener(RequerimientoListener listener) {
		this.listener = listener;
	}
	//TODO ver que le pasan a los inputs, porque no deja tipear los numeros, te obliga a usar las flechitas
	@Override
	public void crearInputs() {
		btnAceptar.setText("Agregar/Actualizar Requerimientos");
		panelInputs.setLayout(new GridLayout(5, 1, 10, 10));

		spinnerLider = crearSpinner();
		spinnerArquitecto = crearSpinner();
		spinnerProgramador = crearSpinner();
		spinnerTester = crearSpinner();

		panelInputs.add(crearFila("Lider De Equipo", spinnerLider));
		panelInputs.add(crearFila("Arquitecto", spinnerArquitecto));
		panelInputs.add(crearFila("Programador", spinnerProgramador));
		panelInputs.add(crearFila("Testers", spinnerTester));

		crearTablaRequerimientos();
		accionesBoton();

		panelInputs.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

		crearTabla();
	}

	private void crearTablaRequerimientos() {
		String[] columnas = { "Líder", "Arquitecto", "Programador", "Tester" };
		modeloTRequerimientos = new DefaultTableModel(columnas, 1) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tablaRequerimientos = new JTable(modeloTRequerimientos);
		tablaRequerimientos.getTableHeader().setReorderingAllowed(false);

		JScrollPane scrollTablaReq = new JScrollPane(tablaRequerimientos);
		scrollTablaReq.setPreferredSize(new Dimension(0, 45));

		panelInputs.add(scrollTablaReq);

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

	private JSpinner crearSpinner() {
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));

		((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);

		return spinner;
	}

	@Override
	public void accionesBoton() {
		btnAceptar.addActionListener(e -> {
			if (listener != null) {
				listener.onRequerimientosAgregados();
			}
		});
	}

	public RequerimientoDto getRequerimientos() {
		return new RequerimientoDto((int) spinnerLider.getValue(), (int) spinnerArquitecto.getValue(),
				(int) spinnerProgramador.getValue(), (int) spinnerTester.getValue());
	}

	// TODO ver si se puede matar esto, porque queda muy cargada la vista y
	// realmente no es necesario conocer las personas cargadas, solo debemos crear y
	// mostrar los requerimientos
	public void actualizarTablaPersonas(ArrayList<PersonaDto> personas) {

		DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

		modelo.setRowCount(0);

		for (PersonaDto p : personas) {

			modelo.addRow(new String[] { p.getNombre(), p.getApellido(), p.getRol().toString(),
					String.valueOf(p.getCalificacion()) });
		}
	}

	public void setRequerimientosActuales(RequerimientoDto dto) {
		if (modeloTRequerimientos != null) {
			modeloTRequerimientos.setValueAt(dto.getLideres(), 0, 0);
			modeloTRequerimientos.setValueAt(dto.getArquitectos(), 0, 1);
			modeloTRequerimientos.setValueAt(dto.getProgramadores(), 0, 2);
			modeloTRequerimientos.setValueAt(dto.getTesters(), 0, 3);
		}
	}

	public void limpiarInputs() {

		spinnerLider.setValue(0);

		spinnerArquitecto.setValue(0);

		spinnerProgramador.setValue(0);

		spinnerTester.setValue(0);
	}

}
