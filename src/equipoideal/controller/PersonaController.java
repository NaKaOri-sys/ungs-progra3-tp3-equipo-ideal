package equipoideal.controller;

import java.io.File;

import equipoideal.model.Persona;
import equipoideal.model.PersonaModel;
import equipoideal.model.dto.PersonaDto;
import equipoideal.model.listener.PersonaListener;
import equipoideal.view.dialogs.EditarPersonaDialog;
import equipoideal.view.dialogs.PersonaDialog;

public class PersonaController implements PersonaListener {

	private PersonaDialog vista;
	private PersonaModel modelo;
	private EditarPersonaDialog editarDialog;
	
	
	
	public PersonaController(PersonaDialog vista, PersonaModel modelo) {
		this.vista = vista;
		this.modelo = modelo;
		this.vista.setPersonasListener(this);
	}

	@Override
	public void onPersonaAgregada() {
		try {
			
			PersonaDto dto = vista.getPersona();
			modelo.agregarPersona(dto); 
			
			vista.ventanaMensaje("Persona agregada correctamente!!");
		} catch (IllegalArgumentException e) {
			vista.ventanaMensaje(e.getMessage());
		}
	}

	@Override
	public void onCargaDesdeJson(String ruta) {

		try {
			modelo.cargarDesdeJSON(ruta);
			vista.ventanaMensaje("Personas agregada correctamente!!");
		} catch (IllegalArgumentException e) {
			vista.ventanaMensaje(e.getMessage());
		}
	}

	@Override
	public void onFotoSeleccionada(File imagen) {
	    try {
	        if (imagen == null || !imagen.exists() || imagen.isDirectory()) {
	            throw new IllegalArgumentException("El archivo seleccionado no es válido o no existe.");
	        }

	        String ruta = modelo.guardarFoto(imagen);

	        
	        if (editarDialog != null && editarDialog.isVisible()) {
	            editarDialog.mostrarImagen(ruta);
	        } else {
	            vista.mostrarImagen(ruta);
	        }

	    } catch (IllegalArgumentException e) {
	        vista.ventanaMensaje(e.getMessage());
	    }
	}

	@Override
	public void onExportarJson(String ruta) {
		modelo.exportarJson(ruta);
		vista.ventanaMensaje("JSON descardo correctamente!!");
	}

	@Override
	public void onLimpiarCache() {
		modelo.limpiarCacheFotos();
		vista.ventanaMensaje("Se eliminó el cache de fotos");
	}

	@Override
	public void onEliminarPersona() {
		int fila = vista.getFilaSeleccionada();

		if (fila == -1) {
			vista.ventanaMensaje("Seleccione una persona.");
			return;
		}
		try {
			modelo.eliminarPersona(fila);
			vista.ventanaMensaje("Persona Eliminada! ");
		} catch (IllegalArgumentException e) {
			vista.ventanaMensaje(e.getMessage());
		}
	}
	

	@Override
	public void onEdicionPersona() {

		int filaSeleccionada = vista.getFilaSeleccionada();

		if (filaSeleccionada == -1) {
			vista.ventanaMensaje("Debe seleccionar una persona.");
			return;
		}

		Persona persona = modelo.getListaPersonas().get(filaSeleccionada);

		PersonaDto dtoPersona = persona.toDto();

		editarDialog = new EditarPersonaDialog("Editar Persona");

		editarDialog.setPersonasListener(this);

		editarDialog.setPersona(dtoPersona);

		editarDialog.setVisible(true);
	}

	@Override
	public void onGuardarEdicionPersona() {
	    try {
	        int filaSeleccionada = vista.getFilaSeleccionada();
	        PersonaDto dto = editarDialog.getPersona();

	        modelo.editarPersona(filaSeleccionada, dto);

	        editarDialog.dispose();
	        editarDialog = null;

	        vista.ventanaMensaje("Persona editada correctamente!!");
	    } catch (IllegalArgumentException e) {
	        editarDialog.ventanaMensaje(e.getMessage());
	    }
	}

}
