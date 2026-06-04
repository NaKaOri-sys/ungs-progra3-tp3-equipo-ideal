package equipoideal.view.components;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import equipoideal.model.dto.PersonaDto;

public class FichaIntegrante extends JPanel {

    private static final Color BG_CARD       = new Color(30, 34, 45);
    private static final Color BG_CARD_HOVER = new Color(40, 46, 62);
    private static final Color BORDER_COLOR  = new Color(55, 62, 80);
    private static final Color TEXT_PRIMARY  = new Color(230, 235, 245);
    private static final Color STAR_COLOR    = new Color(255, 185, 40);
    private static final int CARD_H = 68;
    private static final int RADIUS = 12;

    private boolean hover = false;

    public FichaIntegrante(PersonaDto persona) {
        setLayout(new BorderLayout(12, 0));
        setOpaque(false);
        setBorder(new EmptyBorder(10, 12, 10, 12)); 
        setPreferredSize(new Dimension(340, CARD_H));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, CARD_H));
        setMinimumSize(new Dimension(220, CARD_H));

        add(new PanelAvatar(persona), BorderLayout.WEST);

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel lblNombre = new JLabel(persona.getNombre() + " " + persona.getApellido());
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblNombre.setForeground(TEXT_PRIMARY);
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel filaInferior = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        filaInferior.setOpaque(false);
        filaInferior.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblBadge = UiStyleHelper.crearBadgeRol(persona.getRol().toString());
        JLabel lblCalif = new JLabel("★ " + persona.getCalificacion() + "/5");
        lblCalif.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblCalif.setForeground(STAR_COLOR);

        filaInferior.add(lblBadge);
        filaInferior.add(lblCalif);

        infoPanel.add(Box.createVerticalGlue());
        infoPanel.add(lblNombre);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(filaInferior);
        infoPanel.add(Box.createVerticalGlue());

        add(infoPanel, BorderLayout.CENTER);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { hover = true;  repaint(); }
            public void mouseExited (java.awt.event.MouseEvent e) { hover = false; repaint(); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        UiStyleHelper.activarAntiAliasing(g2);
        
        g2.setColor(hover ? BG_CARD_HOVER : BG_CARD);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), RADIUS, RADIUS));
        
        g2.setColor(BORDER_COLOR);
        g2.setStroke(new BasicStroke(1f));
        g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS));
        
        g2.dispose();
        super.paintComponent(g);
    }
}