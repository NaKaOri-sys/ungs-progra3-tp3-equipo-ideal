package equipoideal.model;

import java.util.List;

import equipoideal.model.dto.ProgresoEventoDto;
import equipoideal.model.dto.ResultadoComparativoDto;
import equipoideal.model.event.IWorkerObserver;
import equipoideal.util.Observable;
import equipoideal.util.OrigenCalculadorEnum;

public class SolucionWorkerModel extends Observable<IWorkerObserver> {
	private ResultadoComparativoDto resultado;

	public SolucionWorkerModel(ResultadoComparativoDto resultado) {
		this.resultado = resultado;
	}

	public void procesarProgreso(List<ProgresoEventoDto> chunks) {
		try {
			for (ProgresoEventoDto progreso : chunks) {
				if (progreso != null) {
					if (progreso.getOrigen() == OrigenCalculadorEnum.BACKTRACKING) {
						this.resultado.setStatsBacktracking(progreso);
						notifyObservers(observer -> observer.onProgress(progreso));
					}
					if (progreso.getOrigen() == OrigenCalculadorEnum.HEURISTICA) {
						this.resultado.setStatsHeuristica(progreso);
					}
				}
			}

		} catch (Exception e) {
				System.err.println("Hubo un error al procesar el progreso, error: " + e.getMessage());
				e.printStackTrace();
			notifyObservers(observer -> observer.onError("Hubo un error al procesar el progreso, error: " + e.getMessage()));
		}
	}

	public void setResultado(ResultadoComparativoDto resultadoDto) {
		if (resultadoDto == null || resultadoDto.getEquipoHeuristica() == null
				|| resultadoDto.getEquipoBacktracking() == null) {
			notifyObservers(o -> o.onError("El equipo resultante no es valido, vuelva a intentarlo"));
			return;
		}
		this.resultado.setEquipoHeuristica(resultadoDto.getEquipoHeuristica());
		this.resultado.setEquipoBacktracking(resultadoDto.getEquipoBacktracking());
		notifyObservers(observer -> observer.onFinish());
	}
	
	public ResultadoComparativoDto getResultado() {
		return resultado;
	}

}
