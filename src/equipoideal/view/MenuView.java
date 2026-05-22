package equipoideal.view;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.UIManager;

public class MenuView extends JPanel {
	
	public MenuView() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		initialize();
	}
	
	public void initialize() {
		setBounds(getBounds());
		setBackground(Color.decode("#121213"));
		setLayout(null);

	}
}
