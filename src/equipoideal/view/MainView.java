package equipoideal.view;

import java.awt.*;
import javax.swing.*;
import equipoideal.util.VentanaEnum;

public class MainView extends JFrame {

	private CardLayout cardLayout;
	private JPanel contenedorPrincipal;

	private MenuView panelMenu;
	private LoadingSolutionPanel panelBusqueda;

	public MainView() {
		setTitle("Equipo Ideal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);

		cardLayout = new CardLayout();
		contenedorPrincipal = new JPanel(cardLayout);

		panelMenu = new MenuView();
		panelBusqueda = new LoadingSolutionPanel();

		contenedorPrincipal.add(panelMenu, VentanaEnum.MENU.toString());
		contenedorPrincipal.add(panelBusqueda, VentanaEnum.BUSQUEDA.toString());

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
	
	public LoadingSolutionPanel getPanelBusqueda() {
		return panelBusqueda;
	}

}
