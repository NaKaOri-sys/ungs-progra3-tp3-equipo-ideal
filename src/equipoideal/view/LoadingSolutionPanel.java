package equipoideal.view;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Font;

public class LoadingSolutionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	JProgressBar progressBar;
	JLabel lblProgress;
	JLabel lblStat;

	public LoadingSolutionPanel() {
		setLayout(null);
		lblProgress = new JLabel("Analizando resultados...");
		lblProgress.setHorizontalAlignment(SwingConstants.CENTER);
		lblProgress.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblProgress.setBounds(108, 80, 247, 61);
		add(lblProgress);

		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(false);
		progressBar.setBounds(27, 151, 390, 27);
		add(this.progressBar);
		
		lblStat = new JLabel("Casos evaluados: 0", SwingConstants.CENTER);
		lblStat.setSize(228, 61);
		lblStat.setLocation(111, 188);
		lblStat.setFont(new Font("SansSerif", Font.BOLD, 18));
		add(lblStat);
	}

	public void actualizarMensaje(String nuevoMensaje) {
		lblProgress.setText(nuevoMensaje);
	}

	public void actualizarEstadisticas(int casosBase) {
		lblStat.setText("Casos base procesados: " + casosBase);
	}

	public void finalizarCarga() {
		progressBar.setIndeterminate(false);
	}
}
