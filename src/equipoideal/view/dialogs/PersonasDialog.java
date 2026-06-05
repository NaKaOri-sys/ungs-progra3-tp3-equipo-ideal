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
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import equipoideal.model.Incompatibilidad;
import equipoideal.model.Persona;
import equipoideal.model.dto.PersonaDto;
import equipoideal.model.listener.PersonasListener;
import equipoideal.util.RolEnum;



public class PersonasDialog extends DialogPadre{
	private static final long serialVersionUID = 1L;
	private JButton btnCargarDesde;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JSpinner spinnerPuntuacion;
	private JComboBox<RolEnum> comboRol;
	private JButton btnFoto;
	private JButton btnExportar;
	private JButton btnLimpiarCache;
	private JPanel panelBotonesNuevos;
	private PersonasListener listener;
	private JLabel lblImagen;
	private JPanel panelFoto;
	private String rutaFoto;


	public PersonasDialog(JDialog frame, String titulo) {
		super(frame, titulo);
		
	}
	
	public void setPersonasListener(PersonasListener listener) {
		this.listener = listener;
	}

	@Override
	public void crearInputs() {
		
		panelInputs.setLayout(new BorderLayout(0, 10));
		
		btnCargarDesde = new JButton("Cargar Personas");
		btnCargarDesde.setPreferredSize(new Dimension(180, 40));
		
		panelBotonesNuevos = new JPanel(new BorderLayout());
		panelBotonesNuevos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		panelBotonesNuevos.setOpaque(false);
	    
		panelFoto = new JPanel(new CardLayout());
		panelFoto.setPreferredSize(new Dimension(120, 120));
	    
		btnFoto = new JButton("Foto");
		lblImagen = new JLabel();
		lblImagen.setHorizontalAlignment(JLabel.CENTER);

		panelFoto.add(btnFoto, "BTN");
		panelFoto.add(lblImagen, "IMG");
		
		panelBotonesNuevos.add(panelFoto, BorderLayout.WEST);
		
		btnExportar = new JButton("Descargar JSON");
		panelBotones.add(btnExportar);
		
		btnLimpiarCache = new JButton("Limpiar cache imagenes");
		panelBotones.add(btnLimpiarCache);

		JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelDerecho.setOpaque(false);
		panelDerecho.add(btnCargarDesde);

		panelBotonesNuevos.add(panelDerecho, BorderLayout.EAST);
	    
	    JPanel nuevosInputs = new JPanel(new GridLayout(4, 2, 10, 10)); 
	    nuevosInputs.setOpaque(false);
		//TODO queda mejor solo Nombre, Apellido, Rol y Puntuacion, sin el "Ingrese"
		JLabel lblNombre = new JLabel("Ingrese Nombre:");
		txtNombre = new JTextField();
		
		JLabel lblApellido = new JLabel("Ingrese Apellido:");
		txtApellido = new JTextField();
		
		JLabel lblPuntuacion = new JLabel("Ingrese Puntos:");
		spinnerPuntuacion = new JSpinner( new SpinnerNumberModel( 1,1,5, 1));
		((JSpinner.DefaultEditor) spinnerPuntuacion.getEditor()).getTextField().setEditable(false);
		//TODO lo mismo que en el todo de arriba, queda mejor solo Rol, sin el "Ingrese"
		JLabel lblRol = new JLabel("Ingrese Puesto:");
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
		
		panelBotonesNuevos.setBorder(BorderFactory.createEmptyBorder(10,40,20,40));
		
		crearTabla();
        accionesBoton();
	}

	@Override
	public void accionesBoton() {
		btnAceptar.addActionListener(e -> {
			if (listener != null) {
				listener.onPersonaAgregada();
			}
			limpiarFoto();
		});
		
		
		btnFoto.addActionListener(e -> {

		    JFileChooser fileChooser = new JFileChooser();
		    fileChooser.setFileFilter(new FileNameExtensionFilter(
		        "Archivos .png .jpg . jpeg", "jpg", "png", "jpeg"
		    ));

		    int result = fileChooser.showOpenDialog(this);

		    if (result == JFileChooser.APPROVE_OPTION) {
		        File imagenSeleccionada = fileChooser.getSelectedFile();
		        
		        if (listener != null) {
					listener.onFotoSeleccionada(imagenSeleccionada);
				}
		    }
		    
		});
		
		btnCargarDesde.addActionListener(e -> {
			
			JFileChooser fileChooser = new JFileChooser();

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

		    fileChooser.setFileFilter(
		        new FileNameExtensionFilter("Archivos JSON", "json")
		    );

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
	            p.getRol(),
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
	
	// GETS
	public String getNombre() {
	    return txtNombre.getText();
	}

	public String getApellido() {
	    return txtApellido.getText();
	}

	public int getPuntos() {
	    return (int) spinnerPuntuacion.getValue();
	}

	public RolEnum getRol() {
	    return (RolEnum) comboRol.getSelectedItem();
	}

	public String getRutaFoto() {
	    return rutaFoto;
	}
	
	
	
}
