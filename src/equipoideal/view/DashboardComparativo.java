package equipoideal.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import equipoideal.model.dto.ResultadoComparativoDto;
import equipoideal.model.listener.IListenerDashboardComparativo;
import equipoideal.util.Observable;
import equipoideal.view.components.PanelResultadoEquipo;

public class DashboardComparativo extends JPanel {
	private static final long serialVersionUID = 1L;
	private Observable<IListenerDashboardComparativo> listener;
	private JButton btnMenuPrincipal;
	private PanelResultadoEquipo panelBacktracking;
	private PanelResultadoEquipo panelHeuristica;

	public DashboardComparativo() {
		listener = new Observable<IListenerDashboardComparativo>();
		setLayout(new BorderLayout());
		JPanel panelSuperior = new JPanel(new BorderLayout());
		panelSuperior.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		btnMenuPrincipal = new JButton("← Volver al Menú Principal");
		btnMenuPrincipal.addActionListener(e -> listener.notifyObservers(o -> o.onMenuPrincipalPress()));
		btnMenuPrincipal.setFont(new Font("Arial", Font.BOLD, 12));
		btnMenuPrincipal.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panelSuperior.add(btnMenuPrincipal, BorderLayout.WEST);
		this.add(panelSuperior, BorderLayout.NORTH);
		JPanel panelCentral = new JPanel(new GridLayout(1, 2, 20, 0));
		panelBacktracking = new PanelResultadoEquipo("Resultado Backtracking", Color.BLUE);
		panelHeuristica = new PanelResultadoEquipo("Resultado Heurística", new Color(46, 139, 87));

		panelCentral.add(panelBacktracking);
		panelCentral.add(panelHeuristica);

		this.add(panelCentral, BorderLayout.CENTER);
	}

	public void renderizarResultados(ResultadoComparativoDto res) {
		panelBacktracking.actualizar(res.getEquipoBacktracking(), res.getStatsBacktracking());
		panelHeuristica.actualizar(res.getEquipoHeuristica(), res.getStatsHeuristica());
	}
	
	public Observable<IListenerDashboardComparativo> getListener(){
		return this.listener;
	}
}