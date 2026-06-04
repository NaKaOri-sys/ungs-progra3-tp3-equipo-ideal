package equipoideal.view;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import equipoideal.model.dto.ResultadoComparativoDto;
import equipoideal.model.listener.IListenerDashboardComparativo;
import equipoideal.util.Observable;
import equipoideal.view.components.PanelResultadoEquipo;
import equipoideal.view.components.UiStyleHelper;

public class DashboardComparativo extends JPanel {
	private static final long serialVersionUID = 1L;

	private Observable<IListenerDashboardComparativo> listener;
	private PanelResultadoEquipo panelBacktracking;
	private PanelResultadoEquipo panelHeuristica;

	public DashboardComparativo() {
		listener = new Observable<IListenerDashboardComparativo>();
		setLayout(new BorderLayout(0, 0));
		setBackground(UiStyleHelper.BG_COLOR);
		add(crearTopBar(), BorderLayout.NORTH);

		JPanel center = new JPanel(new GridLayout(1, 2, 16, 0));
		center.setOpaque(false);
		center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		panelBacktracking = new PanelResultadoEquipo("Resultado Backtracking", new Color(99, 102, 241));
		panelHeuristica = new PanelResultadoEquipo("Resultado Heurística", new Color(16, 185, 129));
		center.add(panelBacktracking);
		center.add(panelHeuristica);

		add(center, BorderLayout.CENTER);
	}

	private JPanel crearTopBar() {
		JPanel bar = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setColor(new Color(40, 46, 62));
				g2.fillRect(0, getHeight() - 1, getWidth(), 1);
				g2.dispose();
			}
		};
		bar.setBackground(new Color(20, 23, 33));
		bar.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

		JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
		left.setOpaque(false);

		JPanel titulos = new JPanel();
		titulos.setOpaque(false);
		titulos.setLayout(new BoxLayout(titulos, BoxLayout.Y_AXIS));

		JLabel lblTitle = new JLabel("Equipo Ideal — Análisis Comparativo");
		lblTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblTitle.setForeground(new Color(230, 235, 245));
		titulos.add(lblTitle);
		titulos.add(Box.createVerticalStrut(2));
		left.add(titulos);
		JButton btnBack = crearBotonVolver();

		bar.add(left, BorderLayout.WEST);
		bar.add(btnBack, BorderLayout.EAST);
		return bar;
	}

	private JButton crearBotonVolver() {
		JButton btn = new JButton("← Volver al Menú") {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				Color bg = getModel().isRollover() ? new Color(45, 50, 68) : new Color(30, 34, 45);
				g2.setColor(bg);
				g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
				g2.setColor(new Color(55, 62, 80));
				g2.setStroke(new BasicStroke(1f));
				g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, getWidth() - 1, getHeight() - 1, 10, 10));
				g2.dispose();
				super.paintComponent(g);
			}
		};
		btn.setFont(new Font("SansSerif", Font.BOLD, 12));
		btn.setForeground(new Color(190, 200, 220));
		btn.setOpaque(false);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
		btn.addActionListener(e -> listener.notifyObservers(o -> o.onMenuPrincipalPress()));
		return btn;
	}

	public void renderizarResultados(ResultadoComparativoDto res) {
		panelBacktracking.actualizar(res.getEquipoBacktracking(), res.getStatsBacktracking());
		panelHeuristica.actualizar(res.getEquipoHeuristica(), res.getStatsHeuristica());
	}

	public Observable<IListenerDashboardComparativo> getListener() {
		return this.listener;
	}
}