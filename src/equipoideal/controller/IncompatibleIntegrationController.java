package equipoideal.controller;

import equipoideal.view.dialogs.IncompatibleDialog;
import equipoideal.model.IncompatibleModel;
import equipoideal.model.Persona;
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
	public void alCrearIncompatibilidad(Persona persona, Persona incompatible) {
		vista.agregarIncompatibilidadTabla(persona, incompatible);
		vista.limpiarInputs();
	}

	@Override
	public void alEliminarIncompatibilidad(Persona persona, Persona incompatible) {
		vista.eliminarFilaDeTabla(persona, incompatible);
	}
}
