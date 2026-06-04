package equipoideal.view.components;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.*;
import equipoideal.model.dto.PersonaDto;

public class PanelAvatar extends JPanel {
    private static final int AVATAR_SIZE = 44;
    private final PersonaDto persona;
    private final Image foto;

    public PanelAvatar(PersonaDto persona) {
        this.persona = persona;
        this.foto = UiStyleHelper.cargarFotoGarantizada(persona.getRutaFoto());
        setOpaque(false);
        setPreferredSize(new Dimension(AVATAR_SIZE, AVATAR_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        UiStyleHelper.activarAntiAliasing(g2);

        int w = getWidth();
        int h = getHeight();

        if (foto != null) {
            g2.setClip(new Ellipse2D.Float(0, 0, w, h));
            g2.drawImage(foto, 0, 0, w, h, null);
            g2.setClip(null);
        } else {
            g2.setColor(new Color(20, 22, 32));
            g2.fillOval(0, 0, w, h);
            
            String ini = obtenerIniciales(persona.getNombre(), persona.getApellido());
            g2.setFont(new Font("SansSerif", Font.BOLD, 14));
            g2.setColor(UiStyleHelper.colorParaRol(persona.getRol().toString()).brighter());
            
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(ini, (w - fm.stringWidth(ini)) / 2, (h - fm.getHeight()) / 2 + fm.getAscent());
        }

        g2.setColor(new Color(255, 255, 255, 35));
        g2.drawOval(0, 0, w - 1, h - 1);
        g2.dispose();
    }
    
    public static String obtenerIniciales(String n, String a) {
		String iN = (n != null && !n.isEmpty()) ? String.valueOf(n.charAt(0)) : "";
		String iA = (a != null && !a.isEmpty()) ? String.valueOf(a.charAt(0)) : "";
		return (iN + iA).toUpperCase();
	}
}
