package equipoideal.controller;

import equipoideal.view.dialogs.IncompatibleDialog;
import equipoideal.model.IncompatibleModel;
import equipoideal.model.event.IObserverIncompatible;

public class IncompatibleIntegrationController implements IObserverIncompatible {

	private IncompatibleDialog vista;
	private IncompatibleModel model;

	public IncompatibleIntegrationController(IncompatibleDialog vista, IncompatibleModel model) {
		this.vista = vista;
		this.model = model;
		
		this.model.addObserver(this);
	}

	@Override
	public void alCrearIncompatibilidad(String persona, String incompatible) {

		vista.agregarIncompatibilidadTabla(persona, incompatible);
		vista.limpiarInputs();
	}
}