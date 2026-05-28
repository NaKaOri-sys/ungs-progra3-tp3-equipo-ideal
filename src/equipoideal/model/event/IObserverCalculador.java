package equipoideal.model.event;

import equipoideal.model.dto.ProgresoEventoDto;

public interface IObserverCalculador {
	void alCambiarProgreso(ProgresoEventoDto evento);
}
