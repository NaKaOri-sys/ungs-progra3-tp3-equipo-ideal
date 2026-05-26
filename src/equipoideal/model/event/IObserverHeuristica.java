package equipoideal.model.event;

import equipoideal.model.dto.EquipoDto;

public interface IObserverHeuristica {
	void onHeuristicaSolved(EquipoDto equipo);
	void onError(String errMessage);
}
