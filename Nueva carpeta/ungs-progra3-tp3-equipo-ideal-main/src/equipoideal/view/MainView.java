package equipoideal.view;

import java.awt.*;
import javax.swing.*;
import equipoideal.util.VentanaEnum;

public class MainView extends JFrame {

	private CardLayout cardLayout;
	private JPanel contenedorPrincipal;

	private MenuView panelMenu;
//	private FormularioView panelFormulario;
//	private IncompatibilidadView panelIncompatibilidad;
//	private RequerimientoView panelRequerimiento;
//	private BusquedaView panelBusqueda;

	public MainView() {
		setTitle("Equipo Ideal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);

		cardLayout = new CardLayout();
		contenedorPrincipal = new JPanel(cardLayout);

		panelMenu = new MenuView();
//		panelFormulario = new FormularioView();
//		panelIncompatibilidad = new IncompatibilidadView();
//		panelRequerimiento = new RequerimientoView();
//		panelBusqueda = new BusquedaView();

		contenedorPrincipal.add(panelMenu, VentanaEnum.MENU.toString());
//		contenedorPrincipal.add(panelFormulario, VentanaEnum.FORMULARIO.toString());
//		contenedorPrincipal.add(panelIncompatibilidad, VentanaEnum.INCOMPATIBILIDAD.toString());
//		contenedorPrincipal.add(panelRequerimiento, VentanaEnum.REQUERIMIENTO.toString());
//		contenedorPrincipal.add(panelBusqueda, VentanaEnum.BUSQUEDA.toString());

		getContentPane().add(contenedorPrincipal, BorderLayout.CENTER);

		setResizable(false);
		revalidate();
		repaint();
	}

	public void navegarA(String nombrePantalla) {
		cardLayout.show(contenedorPrincipal, nombrePantalla);
	}

	public MenuView getPanelMenu() {
		return panelMenu;
	}
	
//	public FormularioView getPanelFormulario() {
//		return panelFormulario;
//	}
	
//	public IncompatibilidadView getPanelIncompatibilidad() {
//		return panelIncompatibilidad;
//	}
	
//	public RequerimientoView getPanelRequerimiento() {
//		return panelRequerimiento;
//	}
	
//	public BusquedaView getPanelBusqueda() {
//		return panelBusqueda;
//	}
	
}
