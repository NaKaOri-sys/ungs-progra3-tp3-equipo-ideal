package equipoideal.controller;

import java.util.ArrayList;
import java.util.List;
import equipoideal.model.Persona;
import equipoideal.model.PersonaModel;
import equipoideal.model.IncompatibleModel;
import equipoideal.model.listener.IncompatiblesListener;
import equipoideal.view.dialogs.IncompatibleDialog;
import equipoideal.view.dialogs.VentanaEmergente;

public class IncompatibleController implements IncompatiblesListener {

	private IncompatibleDialog vista;
	private IncompatibleModel incompatibleModel;
	private List<Persona> listaPersonasTemporal;
	private PersonaModel personaModel;

	// Ahora recibe la lista directa de personas .A
	public IncompatibleController(IncompatibleDialog vista, List<Persona> listaPersonas,
			IncompatibleModel incompatibleModel, PersonaModel personaModel) {
		this.vista = vista;
		this.listaPersonasTemporal = listaPersonas;
		this.incompatibleModel = incompatibleModel;
		this.personaModel = personaModel;

		this.vista.setIncompatiblesListener(this);
	}

	public void refrescarPantalla() {
		// Sincroniza el tamaño de la matriz con los empleados actuales
		this.incompatibleModel.actualizarTamañoMatriz(listaPersonasTemporal.size());
		List<String> nombresCompletos = this.personaModel.obtenerNombresFormateados();
		vista.cargarPersonasEnSelectores(nombresCompletos);
	}

	@Override
	public void onIncompatibilidadRegistrada() {
		int indiceSeleccionadoA = vista.getIndexPersona1();
		int indiceSeleccionadoB = vista.getIndexPersona2();

		if (indiceSeleccionadoA == -1 || indiceSeleccionadoB == -1)
			return;

		if (indiceSeleccionadoA == indiceSeleccionadoB) {
			vista.mostrarMensajeError("Una persona no puede ser incompatible con ella misma.");
			return;
		}

		incompatibleModel.registrarIncompatibilidad(indiceSeleccionadoA, indiceSeleccionadoB);

		String nombreEmpleadoA = this.personaModel.obtenerNombrePorIndice(indiceSeleccionadoA);
		String nombreEmpleadoB = this.personaModel.obtenerNombrePorIndice(indiceSeleccionadoB);

		vista.agregarIncompatibilidadTabla(nombreEmpleadoA, nombreEmpleadoB);
		vista.limpiarInputs();
	}


	
}