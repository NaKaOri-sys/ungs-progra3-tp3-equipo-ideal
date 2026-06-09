package equipoideal.view.dialogs;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
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

		btnAceptar.setText("Agregar Persona");
		crearFormularioPersonas();
		
		String[] columnas = { "Nombre", "Apellido", "Puesto", "Puntos"};
		configurarTabla(columnas, panelCentral);

		btnEditarPersona = crearBoton("Editar");
		btnEliminarPersona = crearBoton("Eliminar");

		JPanel panelAcciones = new JPanel(new GridLayout(2, 1, 0, 15));

		panelAcciones.setOpaque(false);

		panelAcciones.add(btnEditarPersona);
		panelAcciones.add(btnEliminarPersona);

		panelCentral.add(panelAcciones, BorderLayout.EAST);
		
		btnExportar = crearBoton("Exportar JSON");
		btnLimpiarCache = crearBoton("Limpiar Cache de Imagenes");
		
		grillaBotones.add(btnExportar);
		grillaBotones.add(btnLimpiarCache);
		
		
	}

	protected void crearFormularioPersonas() {
		JPanel panelFormulario = new JPanel(new BorderLayout(15, 0));
		panelFormulario.setOpaque(false);
		
		JPanel panelIzquierdo = crearPanel(new BorderLayout());
		
		panelFoto = new JPanel(new CardLayout());
		panelFoto.setOpaque(false);
		
		btnFoto = crearBoton("Seleccionar Foto");
		btnFoto.setPreferredSize(new Dimension(140, 140));
		
		lblImagen = new JLabel();
		lblImagen.setHorizontalAlignment(JLabel.CENTER);
		
		panelFoto.add(btnFoto, "BTN");
		panelFoto.add(lblImagen, "IMG");
		
		panelIzquierdo.add(panelFoto, BorderLayout.CENTER);
		
		
		JPanel panelDerecho = new JPanel(new BorderLayout(0, 8));
		panelDerecho.setOpaque(false);
		
		JPanel panelTopDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		panelTopDerecho.setOpaque(false);
		
		
		JPanel panelInputsGrid = crearPanelConBordeEspecial(new GridLayout(4, 2, 10, 8), colorFondo);
		
		JLabel lblNombre = crearLabel("Nombre:", 12);
		txtNombre = crearTextField(20);
		
		JLabel lblApellido = crearLabel("Apellido:", 12);
		txtApellido = crearTextField(20);
		
		JLabel lblPuntuacion = crearLabel("Puntos:", 12);
		spinnerPuntuacion = crearSpinner(1, 5, 1, 1);
		
		JLabel lblRol = crearLabel("Puesto:", 12);
		comboRol = new JComboBox<RolEnum>(RolEnum.values());
		
		
		panelInputsGrid.add(lblNombre);
		panelInputsGrid.add(lblApellido);
		
		panelInputsGrid.add(txtNombre);
		panelInputsGrid.add(txtApellido);
		
		panelInputsGrid.add(lblPuntuacion);
		panelInputsGrid.add(lblRol);
	
		panelInputsGrid.add(spinnerPuntuacion);
		panelInputsGrid.add(comboRol);
		
		btnCargarDesde = crearBoton("Cargar Desde JSON");
		panelTopDerecho.add(btnCargarDesde);
		
		panelDerecho.add(panelTopDerecho, BorderLayout.NORTH);
		panelDerecho.add(panelInputsGrid, BorderLayout.CENTER);
		
		
		panelFormulario.add(panelIzquierdo, BorderLayout.WEST);
		
		panelFormulario.add(panelDerecho, BorderLayout.CENTER);
		
		panelSuperior.add(panelFormulario, BorderLayout.CENTER);
		
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
