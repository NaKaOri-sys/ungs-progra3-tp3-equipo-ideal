package equipoideal.controller;

import java.util.List;
import equipoideal.model.Persona;
import equipoideal.model.IncompatibleModel;
import equipoideal.model.listener.IncompatiblesListener;
import equipoideal.util.IncompatibleValidator;
import equipoideal.view.dialogs.IncompatibleDialog;

public class IncompatibleController implements IncompatiblesListener {

	private IncompatibleDialog vista;
	private IncompatibleModel incompatibleModel;
	private List<Persona> personas;
	private List<String> nombresFormateados;

	public IncompatibleController(IncompatibleDialog vista, List<Persona> listaPersonas,
			IncompatibleModel incompatibleModel, List<String> nombresFormateados) {
		IncompatibleValidator.validarIncompatible(listaPersonas, nombresFormateados);
		this.vista = vista;
		this.personas = listaPersonas;
		this.incompatibleModel = incompatibleModel;
		this.nombresFormateados = nombresFormateados;
		this.vista.setIncompatiblesListener(this);
		sincronizarDatos();
	}

	public void sincronizarDatos() {
		vista.cargarPersonasEnSelectores(this.nombresFormateados);
	}

	@Override
	public void alRegistrarIncompatibilidad() {
		int indiceSeleccionadoA = vista.getIndexPersona1();
		int indiceSeleccionadoB = vista.getIndexPersona2();

		if (indiceSeleccionadoA == -1 || indiceSeleccionadoB == -1)
			return;

		if (indiceSeleccionadoA == indiceSeleccionadoB) {
			vista.ventanaMensaje("Una persona no puede ser incompatible con ella misma.");
			return;
		}

		incompatibleModel.registrarIncompatibilidad(this.personas.get(indiceSeleccionadoA),
				this.personas.get(indiceSeleccionadoB));
	}

}