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
		
		if (this.incompatibleModel.sonIncompatibles(indiceSeleccionadoA, indiceSeleccionadoB)) {
			vista.ventanaMensaje("Esta incompatibilidad ya se encuentra registrada.");
			return;
		}

		incompatibleModel.registrarIncompatibilidad(this.personas.get(indiceSeleccionadoA),
				this.personas.get(indiceSeleccionadoB));
	}
	
	@Override
    public void onIncompatibilidadEliminada() {
        int filaSeleccionada = vista.getFilaSeleccionada();

        if (filaSeleccionada == -1) {
            vista.ventanaMensaje("Debe seleccionar una fila de la tabla para eliminar la incompatibilidad.");
            return;
        }

        String nombreA = vista.getNombrePersona1DeTabla(filaSeleccionada);
        String nombreB = vista.getNombrePersona2DeTabla(filaSeleccionada);

        int indiceA = buscarIndicePorNombreFormateado(nombreA);
        int indiceB = buscarIndicePorNombreFormateado(nombreB);

        if (indiceA != -1 && indiceB != -1) {
            incompatibleModel.eliminarIncompatibilidad(indiceA, indiceB);
            
            vista.eliminarFilaTabla(filaSeleccionada);
        }
    }

    // traduce los strings de la tabla en posiciones numéricas de la matriz
    private int buscarIndicePorNombreFormateado(String nombreBuscado) {
        for (int i = 0; i < personaModel.getListaPersonas().size(); i++) {
            String nombreFormateado = personaModel.obtenerNombrePorIndice(i);
            if (nombreFormateado.equals(nombreBuscado)) {
                return i;
            }
        }
        return -1;
    }


	
}