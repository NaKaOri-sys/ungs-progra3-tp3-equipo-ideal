package equipoideal.view;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import java.net.URL;

public class LoadingSolutionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	JProgressBar progressBar;
	JLabel lblProgress;
	JLabel lblStat;

	public LoadingSolutionPanel() {
		setLayout(null);
		lblProgress = new JLabel("Analizando resultados...");
		lblProgress.setHorizontalAlignment(SwingConstants.CENTER);
		lblProgress.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblProgress.setBounds(210, 10, 353, 74);
		add(lblProgress);

		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(false);
		progressBar.setBounds(100, 422, 588, 49);
		add(this.progressBar);

		lblStat = new JLabel("Casos evaluados: 0", SwingConstants.CENTER);
		lblStat.setSize(353, 74);
		lblStat.setLocation(210, 481);
		lblStat.setFont(new Font("SansSerif", Font.BOLD, 25));
		add(lblStat);
		buildGifSpinner();
	}

	private void buildGifSpinner() {
		URL url = getClass().getResource("/equipoideal/resources/homer.gif");
		ImageIcon gifIcon = new ImageIcon(url);
		JLabel homerGif = new JLabel(gifIcon);
		homerGif.setBounds(100, 72, 554, 314);
		add(homerGif);
	}

	public void mostrarError(String message) {
		JOptionPane.showMessageDialog(this, message, "Error al procesar solución", JOptionPane.ERROR_MESSAGE);
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
