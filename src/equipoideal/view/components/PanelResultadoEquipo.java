package equipoideal.view.components;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import equipoideal.model.dto.EquipoDto;
import equipoideal.model.dto.PersonaDto;
import equipoideal.model.dto.ProgresoEventoDto;
import equipoideal.util.OrigenCalculadorEnum;

public class PanelResultadoEquipo extends JPanel {

	private static final Color BG_PANEL = new Color(22, 25, 34);
	private static final Color BG_HEADER = new Color(28, 32, 44);
	private static final Color BORDER_COLOR = new Color(55, 62, 80);
	private static final Color TEXT_TITLE = new Color(230, 235, 245);
	private static final Color TEXT_STATS = new Color(140, 150, 170);
	private static final Color BG_STATS_BAR = new Color(28, 32, 44);

	private final Color accentColor;
	private final String titulo;

	private JPanel listaIntegrantes;
	private JLabel lblTiempo;
	private JLabel lblCasos;

	public PanelResultadoEquipo(String titulo, Color accentColor) {
		this.titulo = titulo;
		this.accentColor = accentColor;

		setOpaque(false);
		setLayout(new BorderLayout(0, 0));
		add(crearHeader(), BorderLayout.NORTH);

		listaIntegrantes = new JPanel();
		listaIntegrantes.setLayout(new BoxLayout(listaIntegrantes, BoxLayout.Y_AXIS));
		listaIntegrantes.setOpaque(true);
		listaIntegrantes.setBackground(BG_PANEL);
		listaIntegrantes.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

		JScrollPane scroll = new StylizedScrollBar(listaIntegrantes, BG_PANEL);

		add(scroll, BorderLayout.CENTER);
		add(crearStatsBar(), BorderLayout.SOUTH);
	}

	private JPanel crearHeader() {
		JPanel header = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setColor(BG_HEADER);
				g2.fillRect(0, 0, getWidth(), getHeight());
				g2.setColor(accentColor);
				g2.fillRect(0, 0, 4, getHeight());
				g2.dispose();
				super.paintComponent(g);
			}
		};
		header.setOpaque(false);
		header.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

		JLabel lblTitulo = new JLabel(titulo);
		lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblTitulo.setForeground(TEXT_TITLE);

		JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		left.setOpaque(false);
		left.add(lblTitulo);

		header.add(left, BorderLayout.WEST);
		return header;
	}

	private JPanel crearStatsBar() {
		JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
		bar.setBackground(BG_STATS_BAR);
		bar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR),
				BorderFactory.createEmptyBorder(10, 16, 10, 16)));

		lblTiempo = crearStatLabel("⏱", "Tiempo", "—");
		lblCasos = crearStatLabel("⚙", "Casos base", "—");

		bar.add(lblTiempo);
		bar.add(crearSeparadorVertical());
		bar.add(lblCasos);
		return bar;
	}

	private JLabel crearStatLabel(String icono, String etiqueta, String valor) {
		JLabel lbl = new JLabel(icono + "  " + etiqueta + ": " + valor);
		lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lbl.setForeground(TEXT_STATS);
		return lbl;
	}

	private JSeparator crearSeparadorVertical() {
		JSeparator sep = new JSeparator(JSeparator.VERTICAL);
		sep.setPreferredSize(new Dimension(1, 14));
		sep.setForeground(BORDER_COLOR);
		return sep;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(BG_PANEL);
		g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));
		g2.setColor(BORDER_COLOR);
		g2.setStroke(new BasicStroke(1f));
		g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, getWidth() - 1, getHeight() - 1, 16, 16));
		g2.dispose();
		super.paintComponent(g);
	}

	public void actualizar(EquipoDto equipo, ProgresoEventoDto stats) {
		listaIntegrantes.removeAll();
		for (PersonaDto p : equipo.getIntegrantes()) {
			listaIntegrantes.add(new FichaIntegrante(p));
			listaIntegrantes.add(Box.createVerticalStrut(6));
		}
		if (stats != null) {
			lblTiempo.setText("⏱  Tiempo: " + stats.getTiempo() + " ms");
			if (!stats.getOrigen().equals(OrigenCalculadorEnum.HEURISTICA))
				lblCasos.setText("⚙  Casos base: " + stats.getCasosBaseProcesados());
		}
		revalidate();
		repaint();
	}

	public void mostrarSinSolucion() {
		listaIntegrantes.removeAll();
		JLabel lbl = new JLabel("La heurística no encontró solución para las incompatibilidades dadas.");
		lbl.setForeground(TEXT_STATS);
		lbl.setFont(new Font("SansSerif", Font.ITALIC, 12));
		lbl.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 5));
		listaIntegrantes.add(lbl);
		lblTiempo.setText("⏱  Tiempo: —");
		revalidate();
		repaint();
	}
}
