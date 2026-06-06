package equipoideal.model.event;

import java.util.Map;

import equipoideal.util.RolEnum;

public interface IObserverRequerimiento {

	void onRequerimientosCreados(Map<RolEnum, Integer> nuevosRequerimientos);

}
