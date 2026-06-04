package equipoideal.view.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import equipoideal.util.RolEnum;

public class UiStyleHelper {
	public static final Color BG_COLOR = new Color(15, 17, 24);
	public static final Color ROL_LIDER = new Color(99, 102, 241);
	public static final Color ROL_PROGRAMADOR = new Color(16, 185, 129);
	public static final Color ROL_TESTER = new Color(245, 101, 59);
	public static final Color ROL_ARQUITECTO = new Color(59, 130, 246);
	private static final Color ROL_DEFAULT = new Color(107, 114, 128);
	public static final Color WHITE_TEXT = new Color(255, 255, 255);

	/**
	 * Activa suavizado de gráficos y renderizado de calidad
	 */
	public static void activarAntiAliasing(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	}

	public static Color colorParaRol(String rol) {
		if (rol == null)
			return ROL_DEFAULT;
		return switch (RolEnum.valueOf(rol.toUpperCase())) {
		case LIDER -> ROL_LIDER;
		case PROGRAMADOR -> ROL_PROGRAMADOR;
		case TESTER -> ROL_TESTER;
		case ARQUITECTO -> ROL_ARQUITECTO;
		default -> ROL_DEFAULT;
		};
	}

	public static JLabel crearBadgeRol(String rol) {
		Color color = colorParaRol(rol);
		JLabel lbl = new JLabel(rol.replace("_", " ")) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				activarAntiAliasing(g2);
				g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 25));
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
				g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 80));
				g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 6, 6);
				g2.dispose();
				super.paintComponent(g);
			}
		};
		lbl.setFont(new Font("SansSerif", Font.BOLD, 10));
		lbl.setForeground(color);
		lbl.setBorder(new EmptyBorder(2, 8, 2, 8));
		return lbl;
	}

	public static Image cargarFotoGarantizada(String ruta) {
		if (ruta == null || ruta.isBlank())
			return null;
		ImageIcon ic = new ImageIcon(ruta);
		return ic.getIconWidth() > 0 ? ic.getImage() : null;

	}

	
}
