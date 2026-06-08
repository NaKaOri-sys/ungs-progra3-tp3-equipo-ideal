package equipoideal.model;

import java.util.List;

import javax.management.RuntimeErrorException;

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
			throw new RuntimeException("Hubo un error al procesar el progreso, error: " + e.getMessage());
		}
	}

	public void setResultado(ResultadoComparativoDto resultadoDto) {
		if (resultadoDto == null || resultadoDto.getEquipoHeuristica() == null
				|| resultadoDto.getEquipoBacktracking() == null) {
			throw new RuntimeException("El equipo resultante no es valido, vuelva a intentarlo");
		}
		this.resultado.setEquipoHeuristica(resultadoDto.getEquipoHeuristica());
		this.resultado.setEquipoBacktracking(resultadoDto.getEquipoBacktracking());
		notifyObservers(observer -> observer.onFinish());
	}
	
	public ResultadoComparativoDto getResultado() {
		return resultado;
	}

}
