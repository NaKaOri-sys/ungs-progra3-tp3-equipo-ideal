package equipoideal.view;

import java.awt.Color;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

import equipoideal.model.listener.IMenuListener;
import equipoideal.util.Observable;
import equipoideal.view.dialogs.VentanaEmergente;

public class MenuView extends JPanel {
	private JButton btnCargarPersona;
	private JButton btnRequerimiento;
	private JButton btnIncompatibilidad;
	private JButton btnBusqueda;
	
	private URL urlCargarPersona;
	private URL urlRequerimiento;
	private URL urlIncompatibilidad;
	private URL urlBusqueda;
	
	private Observable <IMenuListener> observable;
	
	public MenuView() {
		
		this.observable = new Observable<>();
		initialize();
	}
	
	public void initialize() {
		setBounds(getBounds());
		setBackground(Color.gray);
		setLayout(null);

		btnCargarPersona = new JButton("Cargar Personas");
		urlCargarPersona = getClass().getResource("/equipoideal/resources/Usuario.png");
		ImageIcon iconoGrandeUsuario = new ImageIcon(urlCargarPersona);
		Image imagenUsuario = iconoGrandeUsuario.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btnCargarPersona.setIcon(new ImageIcon(imagenUsuario));
		btnCargarPersona.setBounds(70, 340, 140, 30);
		btnCargarPersona.addActionListener(e -> {
			observable.notifyObservers(listener -> listener.onCargarPersonas());	
		});
		add(btnCargarPersona);
		
		btnRequerimiento = new JButton("Requerimientos");
		urlRequerimiento = getClass().getResource("/equipoideal/resources/Requerimiento.png");
		ImageIcon iconoGrandeRequerimiento = new ImageIcon(urlRequerimiento);
		Image imagenRequerimiento = iconoGrandeRequerimiento.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btnRequerimiento.setIcon(new ImageIcon(imagenRequerimiento));
		btnRequerimiento.setBounds(335, 340, 140, 30);
		btnRequerimiento.addActionListener(e -> {
			observable.notifyObservers(listener -> listener.onRequerimientos());	
		});
		add(btnRequerimiento);
		
		btnIncompatibilidad = new JButton("Incompatibilidad");
		urlIncompatibilidad = getClass().getResource("/equipoideal/resources/Incompatibilidad.png");
		ImageIcon iconoGrandeIncompatibilidad = new ImageIcon(urlIncompatibilidad);
		Image imagenIncompatibilidad = iconoGrandeIncompatibilidad.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btnIncompatibilidad.setIcon(new ImageIcon(imagenIncompatibilidad));
		btnIncompatibilidad.setBounds(600, 340, 140, 30);
		btnIncompatibilidad.addActionListener(e -> {
			observable.notifyObservers(listener -> listener.onIncompatibilidad());	
		});
		add(btnIncompatibilidad);
		
		btnBusqueda = new JButton("Buscar Equipo");
		urlBusqueda = getClass().getResource("/equipoideal/resources/Busqueda.png");
		ImageIcon iconoGrandeBusqueda = new ImageIcon(urlBusqueda);
		Image imagenBusqueda = iconoGrandeBusqueda.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btnBusqueda.setIcon(new ImageIcon(imagenBusqueda));
		btnBusqueda.setBounds(335, 430, 140, 30);
		btnBusqueda.addActionListener(e -> {
			observable.notifyObservers(listener -> listener.onBusqueda());	
		});
		add(btnBusqueda);
	}
	
	public void mostrarMensajeAdvertencia(String mensaje) {
	    VentanaEmergente aviso = new VentanaEmergente(null, mensaje);
	    aviso.setVisible(true);
	}
	
	public Observable<IMenuListener> obtenerObserver() {
		return this.observable;
	}
	
}
