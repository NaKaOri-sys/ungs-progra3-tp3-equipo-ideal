package equipoideal.controller;

import java.util.List;
import javax.swing.SwingWorker;
import equipoideal.model.CalculadorBacktracking;
import equipoideal.model.Navigation;
import equipoideal.model.dto.ProgresoEventoDto;
import equipoideal.model.event.IObserverBacktracking;
import equipoideal.util.VentanaEnum;
import equipoideal.view.LoadingSolutionPanel;

//TODO este swingWorker va a retornar el equipo resultante obtenido en el worker
public class SolucionWorkerController extends SwingWorker<Void, ProgresoEventoDto> implements IObserverBacktracking {

	private CalculadorBacktracking model;
	private LoadingSolutionPanel viewPanel;
	private Navigation navigation;

	public SolucionWorkerController(CalculadorBacktracking modelo, LoadingSolutionPanel viewPanel, Navigation nav) {
		this.model = modelo;
		this.viewPanel = viewPanel;
		this.navigation = nav;

		this.model.addObserver(this);
	}

	@Override
	protected Void doInBackground() throws Exception {
		return model.calcularMejorEquipo();
	}

	@Override
	public void alCambiarProgreso(ProgresoEventoDto evento) {
		publish(evento);
	}

	@Override
	protected void process(List<ProgresoEventoDto> chunks) {
		ProgresoEventoDto ultimoProgreso = chunks.get(chunks.size() - 1);

		viewPanel.actualizarEstadisticas(ultimoProgreso.getCasosBaseProcesados());
		viewPanel.actualizarMensaje("Mejor puntaje actual: " + ultimoProgreso.getMejorCalificacionActual());
	}

	@Override
	protected void done() {
		// TODO hay que llevar a la siguiente vista el equipo obtenido
		viewPanel.finalizarCarga();
		this.navigation.updateView(VentanaEnum.RESULTADO);
	}
}