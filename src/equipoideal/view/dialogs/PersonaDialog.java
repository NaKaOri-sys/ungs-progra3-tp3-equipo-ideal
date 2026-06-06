package equipoideal.view.dialogs;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import equipoideal.model.dto.PersonaDto;
import equipoideal.model.listener.PersonaListener;
import equipoideal.util.RolEnum;



public class PersonaDialog extends DialogPadre{
	private static final long serialVersionUID = 1L;
	protected JButton btnCargarDesde;
	protected JTextField txtNombre;
	protected JTextField txtApellido;
	protected JSpinner spinnerPuntuacion;
	protected JComboBox<RolEnum> comboRol;
	protected JButton btnFoto;
	private JButton btnExportar;
	private JButton btnLimpiarCache;
	private JButton btnEditarPersona;
	private JButton btnEliminarPersona;
	private JPanel panelBotonesNuevos;
	protected PersonaListener listener;
	private JLabel lblImagen;
	protected JPanel panelFoto;
	protected String rutaFoto;


	public PersonaDialog(String titulo) {
		super(titulo);
		
	}
	
	public void setPersonasListener(PersonaListener listener) {
		this.listener = listener;
	}

	@Override
	public void crearInputs() {

		crearFormularioPersonas();

		btnExportar = new JButton("Descargar JSON");
		panelBotones.add(btnExportar);

		btnLimpiarCache = new JButton("Limpiar cache imagenes");
		panelBotones.add(btnLimpiarCache);

		crearTabla();

		btnEditarPersona = new JButton("Editar");
		btnEliminarPersona = new JButton("Eliminar");

		JPanel panelAccionesTabla = new JPanel();
		panelAccionesTabla.setLayout(new GridLayout(2, 1, 0, 10));

		panelAccionesTabla.add(btnEditarPersona);
		panelAccionesTabla.add(btnEliminarPersona);

		panelLista.add(panelAccionesTabla, BorderLayout.EAST);

		accionesBoton();
	}
	
	protected void crearFormularioPersonas() {

		panelInputs.setLayout(new BorderLayout(0, 10));

		panelBotonesNuevos = new JPanel(new BorderLayout());
		panelBotonesNuevos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		panelBotonesNuevos.setOpaque(false);
		
		btnCargarDesde = new JButton("Cargar Personas");
		btnCargarDesde.setPreferredSize(new Dimension(180, 40));
		
		JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelDerecho.setOpaque(false);
		panelDerecho.add(btnCargarDesde);

		panelBotonesNuevos.add(panelDerecho, BorderLayout.EAST);

		panelFoto = new JPanel(new CardLayout());
		panelFoto.setPreferredSize(new Dimension(120, 120));

		btnFoto = new JButton("Foto");

		lblImagen = new JLabel();
		lblImagen.setHorizontalAlignment(JLabel.CENTER);

		panelFoto.add(btnFoto, "BTN");
		panelFoto.add(lblImagen, "IMG");

		panelBotonesNuevos.add(panelFoto, BorderLayout.WEST);

		JPanel nuevosInputs = new JPanel(new GridLayout(4, 2, 10, 10));
		nuevosInputs.setOpaque(false);

		JLabel lblNombre = new JLabel("Nombre:");
		txtNombre = new JTextField();

		JLabel lblApellido = new JLabel("Apellido:");
		txtApellido = new JTextField();

		JLabel lblPuntuacion = new JLabel("Puntos:");

		spinnerPuntuacion =new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));

		((JSpinner.DefaultEditor)spinnerPuntuacion.getEditor()).getTextField().setEditable(false);

		JLabel lblRol = new JLabel("Puesto:");

		comboRol = new JComboBox<>(RolEnum.values());

		nuevosInputs.add(lblNombre);
		nuevosInputs.add(lblApellido);

		nuevosInputs.add(txtNombre);
		nuevosInputs.add(txtApellido);

		nuevosInputs.add(lblPuntuacion);
		nuevosInputs.add(lblRol);

		nuevosInputs.add(spinnerPuntuacion);
		nuevosInputs.add(comboRol);

		panelInputs.add(panelBotonesNuevos, BorderLayout.NORTH);
		panelInputs.add(nuevosInputs, BorderLayout.CENTER);

		panelBotonesNuevos.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
	}

	@Override
	public void accionesBoton() {
		btnAceptar.addActionListener(e -> {
			if (listener != null) {
				listener.onPersonaAgregada();
			}
			limpiarFoto();
		});
		
		
		btnFoto.addActionListener(e -> cargarImagen());
		
		btnCargarDesde.addActionListener(e -> {
			
			JFileChooser fileChooser = new JFileChooser();
			
			fileChooser.setDialogTitle("Elegir archivo JSON");

		    fileChooser.setFileFilter( new FileNameExtensionFilter("Archivos .json", "json"));

		    int resultado = fileChooser.showOpenDialog(this);

		    if (resultado == JFileChooser.APPROVE_OPTION) {
			    File archivo = fileChooser.getSelectedFile();
			    
		    	if (listener != null) {
					listener.onCargaDesdeJson(archivo.getAbsolutePath());
				}
		    }
		});
		
		btnExportar.addActionListener(e -> {

		    JFileChooser fileChooser = new JFileChooser();

		    fileChooser.setDialogTitle("Guardar JSON");

		    fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos JSON", "json"));

		    int resultado = fileChooser.showSaveDialog(this);

		    if (resultado == JFileChooser.APPROVE_OPTION) {

		        File archivo = fileChooser.getSelectedFile();

		        String ruta = archivo.getAbsolutePath();

		        if (!ruta.endsWith(".json")) {
		            ruta += ".json";
		        }

		        if (listener != null) {
		            listener.onExportarJson(ruta);
		        }
		    }
		});
		
		btnLimpiarCache.addActionListener(e -> {
			if (listener != null) {
	            listener.onLimpiarCache();
	        }
		});
		
		btnEditarPersona.addActionListener(e -> {
			if (listener != null) {
	            listener.onEdicionPersona();
	        }
		});
		
		btnEliminarPersona.addActionListener(e -> {
			if (listener != null) {
	            listener.onEliminarPersona();
	        }
		});
	}
	
	
	
	protected void cargarImagen() {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Elegir Imagen");
		fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos .png .jpg . jpeg", "jpg", "png", "jpeg"));

		int result = fileChooser.showOpenDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {

			File imagenSeleccionada = fileChooser.getSelectedFile();

			if (listener != null) {
				listener.onFotoSeleccionada(imagenSeleccionada);
			}
		}
	}
	
	public void mostrarImagen(String rutaFoto) {
		this.rutaFoto = rutaFoto;

	    ImageIcon icon = new ImageIcon(rutaFoto);
	    Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        
        lblImagen.setIcon(icon);
       
        
        CardLayout cl = (CardLayout) panelFoto.getLayout();
		cl.show(panelFoto, "IMG");
	}
	
	public void actualizarTablaPersonas(ArrayList<PersonaDto> personas) {

	    DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

	    modelo.setRowCount(0);

	    for (PersonaDto p : personas) {

	        modelo.addRow(new String[] {
	            p.getNombre(),
	            p.getApellido(),
	            p.getRol().toString(),
	            String.valueOf(p.getCalificacion())
	        });
	    }

	    limpiarInputs();
	}
	
	public void limpiarInputs() {
	    txtNombre.setText("");
	    txtApellido.setText("");
	    spinnerPuntuacion.setValue(1);
	    comboRol.setSelectedIndex(0);
	    txtNombre.requestFocus();
	}
	
	private void limpiarFoto() {
	    CardLayout cl = (CardLayout) panelFoto.getLayout();

	    cl.show(panelFoto, "BTN");

	    lblImagen.setIcon(null);

	    rutaFoto = null;
	}
	
	public PersonaDto getPersona() {
		return new PersonaDto(
				txtNombre.getText(), 
				txtApellido.getText(), 
				(int) spinnerPuntuacion.getValue(), 
				(RolEnum) comboRol.getSelectedItem(),
				rutaFoto);
	}
	
	public int getFilaSeleccionada() {
		return tabla.getSelectedRow();
	}

}
