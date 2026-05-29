package equipoideal.controller;

import java.util.List;

import javax.swing.SwingWorker;
import equipoideal.model.CalculadorSolucion;
import equipoideal.model.Navigation;
import equipoideal.model.dto.ProgresoEventoDto;
import equipoideal.util.OrigenCalculadorEnum;
import equipoideal.model.dto.ResultadoComparativoDto;
import equipoideal.model.event.IObserverCalculador;
import equipoideal.util.VentanaEnum;
import equipoideal.view.LoadingSolutionPanel;

public class SolucionWorkerController extends SwingWorker<ResultadoComparativoDto, ProgresoEventoDto>
		implements IObserverCalculador {

	private LoadingSolutionPanel viewPanel;
	private Navigation navigation;
	private ResultadoComparativoDto resultado;
	private CalculadorSolucion facade;

	public SolucionWorkerController(CalculadorSolucion facade,
			LoadingSolutionPanel viewPanel, Navigation nav, ResultadoComparativoDto resultado) {
		this.viewPanel = viewPanel;
		this.navigation = nav;
		this.resultado = resultado;
		this.facade = facade;
		this.facade.addObserver(this);
	}

	@Override
	protected ResultadoComparativoDto doInBackground() throws Exception {
		try {
			return this.facade.calcularSolucionGlobal();
		} catch (Exception e) {
			System.err.println("Hubo un error al procesar el worker, error: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void alCambiarProgreso(ProgresoEventoDto evento) {
		publish(evento);
	}

	@Override
	protected void process(List<ProgresoEventoDto> chunks) {
		if (chunks == null || chunks.isEmpty())
			return;
		ProgresoEventoDto ultimoProgreso = chunks.get(chunks.size() - 1);
		if (ultimoProgreso == null)
			return;
		if (ultimoProgreso.getOrigen() == OrigenCalculadorEnum.BACKTRACKING) {
			this.resultado.setStatsBacktracking(ultimoProgreso);
			viewPanel.actualizarEstadisticas(ultimoProgreso.getCasosBaseProcesados());
			viewPanel.actualizarMensaje("Tiempo transcurrido (backtracking): " + ultimoProgreso.getTiempo());
		}
		if (ultimoProgreso.getOrigen() == OrigenCalculadorEnum.HEURISTICA) {
			this.resultado.setStatsHeuristica(ultimoProgreso);
			viewPanel.actualizarMensaje("Tiempo transcurrido (heurística): " + ultimoProgreso.getTiempo());
		}
	}

	@Override
	protected void done() {
		this.facade.removeObserver(this);
		try {
			ResultadoComparativoDto resultadoDto = get();
			if (resultadoDto == null) {
				this.viewPanel.mostrarError("El equipo resultante no es valido, vuelva a intentarlo");
				this.navigation.updateView(VentanaEnum.MENU);
				return;
			}
			viewPanel.finalizarCarga();
			this.resultado = resultadoDto;
			this.navigation.updateView(VentanaEnum.RESULTADO);
		} catch (Exception e) {
			this.viewPanel.mostrarError("Hubo un error al procesar el worker, error: " + e.getMessage());
			this.navigation.updateView(VentanaEnum.MENU);
			return;
		}
	}

	public void dispose() {
		this.facade.removeObserver(this);
	}
}