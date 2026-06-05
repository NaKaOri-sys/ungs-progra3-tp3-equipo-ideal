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

	public SolucionWorkerController(CalculadorSolucion facade, LoadingSolutionPanel viewPanel, Navigation nav,
			ResultadoComparativoDto resultado) {
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

	// TODO ver si se puede crear un modelo para el worker asi el controller queda
	// libre de logica de negocio y se encarga solo de la comunicacion entre el
	// modelo y la vista, y el modelo se encarga de procesar los eventos y
	// actualizar el resultado. Esto permitiria que el controller quede mas limpio y
	// enfocado en la comunicacion, y el modelo se encargue de la logica de negocio
	// y procesamiento de eventos.
	@Override
	protected void process(List<ProgresoEventoDto> chunks) {
		if (chunks == null || chunks.isEmpty())
			return;
		for (ProgresoEventoDto progreso : chunks) {
			if (progreso != null) {
				if (progreso.getOrigen() == OrigenCalculadorEnum.BACKTRACKING) {
					this.resultado.setStatsBacktracking(progreso);
					viewPanel.actualizarEstadisticas(progreso.getCasosBaseProcesados());
					viewPanel.actualizarMensaje("Tiempo transcurrido (backtracking): " + progreso.getTiempo() + "ms.");
				}
				if (progreso.getOrigen() == OrigenCalculadorEnum.HEURISTICA) {
					this.resultado.setStatsHeuristica(progreso);
				}
			}
		}
	}

	@Override
	protected void done() {
		this.facade.removeObserver(this);
		try {
			ResultadoComparativoDto resultadoDto = get();
			if (resultadoDto == null || resultadoDto.getEquipoHeuristica() == null
					|| resultadoDto.getEquipoBacktracking() == null) {
				this.viewPanel.mostrarError("El equipo resultante no es valido, vuelva a intentarlo");
				this.navigation.updateView(VentanaEnum.MENU);
				return;
			}
			viewPanel.finalizarCarga();
			this.resultado.setEquipoHeuristica(resultadoDto.getEquipoHeuristica());
			this.resultado.setEquipoBacktracking(resultadoDto.getEquipoBacktracking());
			this.navigation.updateView(VentanaEnum.RESULTADO);
		} catch (Exception e) {
			System.err.println(">>> WORKER DONE ERROR: " + e.getMessage());
			this.viewPanel.mostrarError("Hubo un error al procesar el worker, error: " + e.getMessage());
			this.navigation.updateView(VentanaEnum.MENU);
			return;
		}
	}

	public void dispose() {
		this.facade.removeObserver(this);
	}
}