package equipoideal.model.event;

import equipoideal.model.dto.ProgresoEventoDto;

public interface IObserverBacktracking {
	void alCambiarProgreso(ProgresoEventoDto evento);
}
