package equipoideal.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import equipoideal.model.dto.RequerimientoDto;
import equipoideal.model.listener.RequerimientoListener;

public class RequerimientoDialog extends DialogPadre {
	private static final long serialVersionUID = 1L;
	
	private JSpinner spinnerLider;
	private JSpinner spinnerArquitecto;
	private JSpinner spinnerProgramador;
	private JSpinner spinnerTester;
	private RequerimientoListener listener;
	private JLabel lblLider;
	private JLabel lblArquitecto;
	private JLabel lblProgramador;
	private JLabel lblTester;

	public RequerimientoDialog(String titulo) {
		super(titulo);
	}

	public void setRequerimientosListener(RequerimientoListener listener) {
		this.listener = listener;
	}

	@Override
	public void crearInputs() {
		btnAceptar.setText("Agregar/Actualizar Requerimientos");

	    panelPrincipal.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		JPanel panelSpinners = crearPanel(new GridLayout(4, 1));
		JPanel panelEtiquetas = new JPanel(new FlowLayout());
		panelEtiquetas.setOpaque(false);
		
		
		spinnerLider = crearSpinner(0, 99, 0, 1);
		spinnerArquitecto = crearSpinner(0, 99, 0, 1);
		spinnerProgramador = crearSpinner(0, 99, 0, 1);
		spinnerTester = crearSpinner(0, 99, 0, 1);

		panelSpinners.add(crearFila("Lider De Equipo", spinnerLider));
		panelSpinners.add(crearFila("Arquitecto", spinnerArquitecto));
		panelSpinners.add(crearFila("Programador", spinnerProgramador));
		panelSpinners.add(crearFila("Tester", spinnerTester));
		
		lblLider = crearLabel("0", 12);
	    lblArquitecto = crearLabel("0", 12);
	    lblProgramador = crearLabel("0", 12);
	    lblTester = crearLabel("0", 12);
		
		panelEtiquetas.add(requerimientoPedido("Lider(es)", lblLider));
		panelEtiquetas.add(requerimientoPedido("Arquitecto(s)", lblArquitecto));
		panelEtiquetas.add(requerimientoPedido("Programador(es)", lblProgramador));
		panelEtiquetas.add(requerimientoPedido("Tester(s)", lblTester));

		panelSuperior.add(panelSpinners, BorderLayout.NORTH);
		panelSuperior.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
		panelCentral.add(panelEtiquetas, BorderLayout.CENTER);
	}
	
	private JPanel requerimientoPedido(String rol, JLabel lblCantidad) {

	    JPanel etiqueta = crearPanel(new FlowLayout());
	    etiqueta.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 10));
	    
	    JPanel cuadroNumero = new JPanel(new BorderLayout());
	    cuadroNumero.setBackground(new Color(60, 65, 85)); 
	    cuadroNumero.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
	    
	    lblCantidad.setHorizontalAlignment(JLabel.CENTER);
	    lblCantidad.setForeground(Color.WHITE);
	    cuadroNumero.add(lblCantidad, BorderLayout.CENTER);
	    
	    JLabel lblTextoRol = crearLabel(rol, 12);
	    lblTextoRol.setForeground(new Color(200, 200, 205));
	    
	    etiqueta.add(cuadroNumero);
	    etiqueta.add(lblTextoRol);
	    
	    return etiqueta;
	}

	@Override
	public void accionesBoton() {
		btnAceptar.addActionListener(e -> {
			if (listener != null) listener.onRequerimientosAgregados();
		});
	}

	private JPanel crearFila(String nombre, JSpinner spinnerFila) {
	    JPanel fila = crearPanel(new BorderLayout());
	    
	    JLabel lblFila = crearLabel(nombre, 12);
	    lblFila.setPreferredSize(new Dimension(160, 30));
	    
	    fila.add(lblFila, BorderLayout.WEST);
	    fila.add(spinnerFila, BorderLayout.EAST);
	    return fila;
	}
	

	public RequerimientoDto getRequerimientos() {
		return new RequerimientoDto((int) spinnerLider.getValue(), (int) spinnerArquitecto.getValue(),
				(int) spinnerProgramador.getValue(), (int) spinnerTester.getValue());
	}

	public void setRequerimientosActuales(RequerimientoDto dto) {
		lblLider.setText(String.valueOf(dto.getLideres()));
        lblArquitecto.setText(String.valueOf(dto.getArquitectos()));
        lblProgramador.setText(String.valueOf(dto.getProgramadores()));
        lblTester.setText(String.valueOf(dto.getTesters()));
	}

	@Override
	public void limpiarInputs() {
		spinnerLider.setValue(0);
		spinnerArquitecto.setValue(0);
		spinnerProgramador.setValue(0);
		spinnerTester.setValue(0);
	}
}