package equipoideal.controller;


import equipoideal.model.RequerimientoModel;
import equipoideal.model.dto.RequerimientoDto;
import equipoideal.model.event.IObserverRequerimiento;
import equipoideal.view.dialogs.RequerimientoDialog;

public class RequerimientoIntegrationController implements IObserverRequerimiento {

	private RequerimientoDialog vista;

	public RequerimientoIntegrationController(RequerimientoDialog vista, RequerimientoModel modelo) {
		this.vista = vista;
		modelo.addObserver(this);		
	}
	@Override
	public void onRequerimientosCreados(RequerimientoDto nuevosRequerimientos) {
        vista.setRequerimientosActuales(nuevosRequerimientos);
	}

}