package equipoideal.model.event;



import equipoideal.model.dto.RequerimientoDto;

public interface IObserverRequerimiento {

	void onRequerimientosCreados(RequerimientoDto nuevosRequerimientos);

}
