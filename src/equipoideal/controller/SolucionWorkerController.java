package equipoideal.controller;

import java.util.List;

import javax.swing.SwingWorker;
import equipoideal.model.CalculadorSolucion;
import equipoideal.model.Navigation;
import equipoideal.model.SolucionWorkerModel;
import equipoideal.model.dto.ProgresoEventoDto;
import equipoideal.util.OrigenCalculadorEnum;
import equipoideal.model.dto.ResultadoComparativoDto;
import equipoideal.model.event.IObserverCalculador;
import equipoideal.model.event.IWorkerObserver;
import equipoideal.util.VentanaEnum;
import equipoideal.view.LoadingSolutionPanel;

public class SolucionWorkerController extends SwingWorker<ResultadoComparativoDto, ProgresoEventoDto>
		implements IObserverCalculador, IWorkerObserver {

	private LoadingSolutionPanel viewPanel;
	private Navigation navigation;
	private CalculadorSolucion facade;
	private SolucionWorkerModel model;

	public SolucionWorkerController(CalculadorSolucion facade, LoadingSolutionPanel viewPanel, Navigation nav,
			SolucionWorkerModel model) {
		this.viewPanel = viewPanel;
		this.model = model;
		this.navigation = nav;
		this.facade = facade;
		this.facade.addObserver(this);
		this.model.addObserver(this);
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
		this.model.procesarProgreso(chunks);
	}

	@Override
	protected void done() {
		this.facade.removeObserver(this);
		try {
			this.model.setResultado(get());
		} catch (Exception e) {
			System.err.println(">>> WORKER DONE ERROR: " + e.getMessage());
		}
	}

	public void dispose() {
		this.facade.removeObserver(this);
	}

	@Override
	public void onFinish() {
		viewPanel.finalizarCarga();
		this.navigation.updateView(VentanaEnum.RESULTADO);
	}

	@Override
	public void onProgress(ProgresoEventoDto progress) {
		viewPanel.actualizarEstadisticas(progress.getCasosBaseProcesados());
		viewPanel.actualizarMensaje("Tiempo transcurrido (backtracking): " + progress.getTiempo() + "ms.");
	}

	@Override
	public void onError(String error) {
		viewPanel.mostrarError(error);
		this.navigation.updateView(VentanaEnum.MENU);
	}
}