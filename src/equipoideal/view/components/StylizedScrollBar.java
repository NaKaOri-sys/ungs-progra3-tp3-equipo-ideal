package equipoideal.view.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class StylizedScrollBar extends JScrollPane {

	public StylizedScrollBar(JPanel listaIntegrantes, Color bgPanel) {
		setViewportView(listaIntegrantes);
		setOpaque(false);
		getViewport().setOpaque(false);
		getViewport().setBackground(bgPanel);
		setBorder(BorderFactory.createEmptyBorder());
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getVerticalScrollBar().setUnitIncrement(16);
		getHorizontalScrollBar().setUnitIncrement(16);
		estilizarScrollBar(this, bgPanel);
	}
	
	private void estilizarScrollBar(JScrollPane scroll, Color bgPanel) {
		JScrollBar vsb = scroll.getVerticalScrollBar();
		JScrollBar hsb = scroll.getHorizontalScrollBar();
		vsb.setPreferredSize(new Dimension(6, 0));
		hsb.setPreferredSize(new Dimension(0, 6));
		vsb.setUI(setBasicScrollBarUI(bgPanel));
		hsb.setUI(setBasicScrollBarUI(bgPanel));
	}
	
	private BasicScrollBarUI setBasicScrollBarUI(Color bgColor) {
		return new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				thumbColor = new Color(70, 80, 100);
				trackColor = bgColor;
			}

			@Override
			protected JButton createDecreaseButton(int o) {
				return botonInvisible();
			}

			@Override
			protected JButton createIncreaseButton(int o) {
				return botonInvisible();
			}

			private JButton botonInvisible() {
				JButton b = new JButton();
				b.setPreferredSize(new Dimension(0, 0));
				return b;
			}
		};
	}
}
