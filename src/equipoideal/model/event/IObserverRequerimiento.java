package equipoideal.model.event;

import java.util.ArrayList;
import equipoideal.model.Requerimiento;

public interface IObserverRequerimiento {

	void onRequerimientosCreados(ArrayList<Requerimiento> nuevosRequerimientos);

}
