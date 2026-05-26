package equipoideal.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;
import equipoideal.model.CalculadorBacktracking;
import equipoideal.model.Navigation;
import equipoideal.model.dto.EquipoDto;
import equipoideal.model.dto.ProgresoEventoDto;
import equipoideal.model.dto.ResultadoComparativoDto;
import equipoideal.model.event.IObserverBacktracking;
import equipoideal.util.VentanaEnum;
import equipoideal.view.DashboardComparativo;
import equipoideal.view.LoadingSolutionPanel;

public class SolucionWorkerController extends SwingWorker<EquipoDto, ProgresoEventoDto>
		implements IObserverBacktracking {

	private CalculadorBacktracking model;
	private LoadingSolutionPanel viewPanel;
	private Navigation navigation;
	private ResultadoComparativoDto resultado;

	public SolucionWorkerController(CalculadorBacktracking modelo, LoadingSolutionPanel viewPanel, Navigation nav, ResultadoComparativoDto resultado) {
		this.model = modelo;
		this.viewPanel = viewPanel;
		this.navigation = nav;
		this.resultado = resultado;

		this.model.addObserver(this);
	}

	@Override
	protected EquipoDto doInBackground() throws Exception {
		try {
			return model.calcularMejorEquipo();
		} catch (Exception e) {
			System.err.println("Hubo un error al procesar el worker, error: " + e.getMessage());
			return new EquipoDto(new ArrayList<>());
		}
	}

	@Override
	public void alCambiarProgreso(ProgresoEventoDto evento) {
		publish(evento);
	}

	@Override
	protected void process(List<ProgresoEventoDto> chunks) {
		ProgresoEventoDto ultimoProgreso = chunks.get(chunks.size() - 1);
		// TODO: Validar que ultimoProgreso no sea null antes de usar
		this.resultado.setStatsBacktracking(ultimoProgreso);
		viewPanel.actualizarEstadisticas(ultimoProgreso.getCasosBaseProcesados());
		viewPanel.actualizarMensaje("Tiempo transcurrido: " + ultimoProgreso.getTiempo());
	}

	@Override
	protected void done() {
		model.removeObserver(this);
		try {
			EquipoDto equipoResultante = get();
			viewPanel.finalizarCarga();
			this.resultado.setEquipoBacktracking(equipoResultante);
			this.navigation.updateView(VentanaEnum.RESULTADO);
		} catch (Exception e) {
			this.viewPanel.mostrarError("Hubo un error al procesar el worker, error: " + e.getMessage());
			this.navigation.updateView(VentanaEnum.MENU);
			return;
		}
	}
}