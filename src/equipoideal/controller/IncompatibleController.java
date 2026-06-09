package equipoideal.controller;

import java.util.List;
import equipoideal.model.Persona;
import equipoideal.model.dto.PersonaDto;
import equipoideal.model.IncompatibleModel;
import equipoideal.model.listener.IncompatiblesListener;
import equipoideal.util.IncompatibleValidator;
import equipoideal.util.PersonaUtil;
import equipoideal.view.dialogs.IncompatibleDialog;

public class IncompatibleController implements IncompatiblesListener {

	private IncompatibleDialog vista;
	private IncompatibleModel incompatibleModel;
	private List<Persona> personas;

	public IncompatibleController(IncompatibleDialog vista, List<Persona> listaPersonas,
			IncompatibleModel incompatibleModel) {
		IncompatibleValidator.validarIncompatible(listaPersonas);
		this.vista = vista;
		this.personas = listaPersonas;
		this.incompatibleModel = incompatibleModel;
		this.vista.setIncompatiblesListener(this);
		sincronizarDatos();
	}

	public void sincronizarDatos() {
		vista.cargarPersonasEnSelectores(PersonaUtil.personasToDto(this.personas));
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

		Persona persona = this.personas.get(indiceSeleccionadoA);
		Persona incompatible = this.personas.get(indiceSeleccionadoB);

		if (this.incompatibleModel.sonIncompatibles(persona, incompatible)) {
			vista.ventanaMensaje("Esta incompatibilidad ya se encuentra registrada.");
			return;
		}

		incompatibleModel.registrarIncompatibilidad(persona, incompatible);
	}

	@Override
	public void alBorrarIncompatibilidad() {
		int filaSeleccionada = vista.getFilaSeleccionada();
		if (filaSeleccionada == -1) {
			vista.ventanaMensaje("Debe seleccionar una fila de la tabla para eliminar la incompatibilidad.");
			return;
		}

		Persona persona = vista.getPersona1DeTabla(filaSeleccionada);
		Persona incompatible = vista.getPersona2DeTabla(filaSeleccionada);
		
		this.incompatibleModel.eliminarIncompatibilidad(persona, incompatible);
	}
}