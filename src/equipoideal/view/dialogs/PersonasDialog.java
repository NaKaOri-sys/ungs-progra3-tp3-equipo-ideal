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
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import equipoideal.model.dto.PersonaDto;
import equipoideal.model.listener.PersonasListener;



public class PersonasDialog extends DialogPadre{
	private static final long serialVersionUID = 1L;
	private JButton btnCargarDesde;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtPuntuacion;
	private JTextField txtRol;
	private JButton btnFoto;
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

		JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelDerecho.setOpaque(false);
		panelDerecho.add(btnCargarDesde);

		panelBotonesNuevos.add(panelDerecho, BorderLayout.EAST);
	    
	    JPanel nuevosInputs = new JPanel(new GridLayout(4, 2, 10, 10)); 
	    nuevosInputs.setOpaque(false);
		
		JLabel lblNombre = new JLabel("Ingrese Nombre:");
		txtNombre = new JTextField();
		
		JLabel lblApellido = new JLabel("Ingrese Apellido:");
		txtApellido = new JTextField();
		
		JLabel lblPuntuacion = new JLabel("Ingrese Puntos:");
		txtPuntuacion = new JTextField();
		
		JLabel lblRol = new JLabel("Ingrese Puesto:");
		txtRol = new JTextField();
		
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
		        "Imágenes", "jpg", "png", "jpeg"
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

		    fileChooser.setFileFilter( new FileNameExtensionFilter("JSON y TXT", "json", "txt"));

		    int resultado = fileChooser.showOpenDialog(this);

		    if (resultado == JFileChooser.APPROVE_OPTION) {
			    File archivo = fileChooser.getSelectedFile();
			    
		    	if (listener != null) {
					listener.onCargaDesdeJson(archivo.getAbsolutePath());
				}
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
	    txtPuntuacion.setText("");
	    txtRol.setText("");
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

	public String getPuntos() {
	    return txtPuntuacion.getText();
	}

	public String getRol() {
	    return txtRol.getText();
	}

	public String getRutaFoto() {
	    return rutaFoto;
	}
}
