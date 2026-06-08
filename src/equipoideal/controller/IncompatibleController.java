package equipoideal.controller;

import java.util.ArrayList;
import java.util.List;
import equipoideal.model.Persona;
import equipoideal.model.PersonaModel;
import equipoideal.model.IncompatibleModel;
import equipoideal.model.listener.IncompatiblesListener;
import equipoideal.view.dialogs.IncompatibleDialog;
import equipoideal.model.event.IObserverPersona;

//TODO quitar implementacion de observerPersona, esta clase deberia recibir la lista
//	la dejo asi por ahora ya que sino no anda el programa

public class IncompatibleController implements IncompatiblesListener, IObserverPersona {

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
		
		this.personaModel.addObserver(this);
		

	}
	
	@Override
	public void onListaPersonasModificada(ArrayList<Persona> nuevaLista) {
		sincronizarDatos();
	}
	


	public void sincronizarDatos() {
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
			vista.ventanaMensaje("Una persona no puede ser incompatible con ella misma.");
			return;
		}
		
		if (this.incompatibleModel.sonIncompatibles(indiceSeleccionadoA, indiceSeleccionadoB)) {
			vista.ventanaMensaje("Esta incompatibilidad ya se encuentra registrada.");
			return;
		}

		incompatibleModel.registrarIncompatibilidad(indiceSeleccionadoA, indiceSeleccionadoB);

		String nombreEmpleadoA = this.personaModel.obtenerNombrePorIndice(indiceSeleccionadoA);
		String nombreEmpleadoB = this.personaModel.obtenerNombrePorIndice(indiceSeleccionadoB);

		vista.agregarIncompatibilidadTabla(nombreEmpleadoA, nombreEmpleadoB);
		vista.limpiarInputs();
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