package equipoideal.view.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class DialogStyleHelper {

	public static final Color ColorFondoPrincipal = new Color(22, 25, 34);
	public static final Color ColorFondoBotones = new Color(30, 34, 45);
	public static final Color ColorBorde = new Color(55, 62, 80);
	public static final Color ColorLetra = new Color(240, 230, 210);
	public static final Color ColorBotonHover =new Color(30, 40, 45);
	public static final Font fuentePrincipal = new Font("SansSerif", Font.BOLD, 12);

	public static JPanel crearPanelEstilizado(LayoutManager layout, Color colorBorde) {
	    JPanel panel = new JPanel(layout) {
	        private static final long serialVersionUID = 1L;
	        @Override
	        protected void paintComponent(Graphics g) {
	            Graphics2D g2 = (Graphics2D) g.create();
	            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	            
	            g2.setColor(getBackground());
	            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
	            
	            g2.setColor(colorBorde);
	            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
	            
	            g2.dispose();
	            super.paintComponent(g);
	        }
	    };
	    panel.setOpaque(false); 
	    panel.setBackground(ColorFondoPrincipal);
	    panel.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
	    return panel;
	}

	public static JLabel crearLabelEstilizado(String texto, int tamanoLetra) {
		JLabel label = new JLabel(texto);
		label.setFont(new Font("SansSerif", Font.BOLD, tamanoLetra));
		label.setForeground(ColorLetra);
		return label;
	}

	public static JButton crearBotonEstilizado(String nombre) {
		JButton btn = new JButton(nombre) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				g2.setColor(getModel().isRollover() ? ColorBotonHover : ColorFondoBotones);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
				
				g2.setColor(ColorBorde);
				g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
				
				g2.dispose();
				super.paintComponent(g);
			}
		};
		btn.setFont(fuentePrincipal);
		btn.setForeground(new Color(190, 200, 220));
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
		return btn;
	}

	
	public static JTextField crearTextFieldEstilizado(int cantidadLetras) {
		JTextField txt = new JTextField(cantidadLetras) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				
				g2.setColor(ColorFondoBotones);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
				
				g2.setColor(ColorBorde);
				g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
				
				g2.dispose();
				super.paintComponent(g);
			}
		};
		txt.setFont(fuentePrincipal);
		txt.setForeground(ColorLetra);
		txt.setCaretColor(ColorLetra);
		txt.setOpaque(false);
		txt.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
		return txt;
	}

	public static JSpinner crearSpinnerEstilizado(int min, int max, int actual, int paso) {
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(actual, min, max, paso));
		spinner.setPreferredSize(new Dimension(80, 30));
		
		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
		JTextField campoTexto = editor.getTextField();
		campoTexto.setBackground(ColorFondoBotones);
		campoTexto.setForeground(ColorLetra);
		campoTexto.setFont(fuentePrincipal);
		campoTexto.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		
		
		spinner.setBorder(BorderFactory.createLineBorder(ColorBorde, 1));
		spinner.setBackground(ColorFondoBotones);
		
		return spinner;
	}
	
}