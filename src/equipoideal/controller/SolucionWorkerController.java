package equipoideal.controller;

import java.util.List;

import javax.swing.SwingWorker;
import equipoideal.model.CalculadorBacktracking;
import equipoideal.model.CalculadorHeuristica;
import equipoideal.model.Navigation;
import equipoideal.model.dto.ProgresoEventoDto;
import equipoideal.model.dto.ResultadoComparativoDto;
import equipoideal.model.event.IObserverBacktracking;
import equipoideal.util.VentanaEnum;
import equipoideal.view.LoadingSolutionPanel;

public class SolucionWorkerController extends SwingWorker<ResultadoComparativoDto, ProgresoEventoDto>
		implements IObserverBacktracking {

	private CalculadorBacktracking backtracking;
	private LoadingSolutionPanel viewPanel;
	private Navigation navigation;
	private ResultadoComparativoDto resultado;
	private CalculadorHeuristica heuristica;

	public SolucionWorkerController(CalculadorBacktracking backtracking, CalculadorHeuristica heuristica,
			LoadingSolutionPanel viewPanel, Navigation nav, ResultadoComparativoDto resultado) {
		this.backtracking = backtracking;
		this.heuristica = heuristica;
		this.viewPanel = viewPanel;
		this.navigation = nav;
		this.resultado = resultado;

		this.backtracking.addObserver(this);
	}

	@Override
	protected ResultadoComparativoDto doInBackground() throws Exception {
		try {
			ResultadoComparativoDto res = new ResultadoComparativoDto();
			res.setEquipoBacktracking(this.backtracking.calcularMejorEquipo());
			res.setEquipoHeuristica(this.heuristica.ejecutarHeuristica());

			return res;
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
		this.resultado.setStatsBacktracking(ultimoProgreso);
		viewPanel.actualizarEstadisticas(ultimoProgreso.getCasosBaseProcesados());
		viewPanel.actualizarMensaje("Tiempo transcurrido: " + ultimoProgreso.getTiempo());
	}

	@Override
	protected void done() {
		this.backtracking.removeObserver(this);
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
}