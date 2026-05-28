package equipoideal.controller;

import java.io.File;

import equipoideal.model.PersonaDialogModel;
import equipoideal.model.listener.PersonasListener;
import equipoideal.view.dialogs.PersonasDialog;


public class PersonasController implements PersonasListener {

	private PersonasDialog vista;
	private PersonaDialogModel modelo;

	public PersonasController(PersonasDialog vista, PersonaDialogModel modelo) {
		this.vista = vista;
		this.modelo = modelo;
		this.vista.setPersonasListener(this);
	}

	@Override
	public void onPersonaAgregada() {
	    String nombre = vista.getNombre();
	    String apellido = vista.getApellido();
	    int puntos = Integer.parseInt(vista.getPuntos());
	    String rol = vista.getRol();
	    String ruta = vista.getRutaFoto();
	    
		modelo.agregarPersona(nombre, apellido, puntos, rol, ruta);
	}

	@Override
	public void onCargaDesdeJson(String ruta) {
	    modelo.cargarDesdeJSON(ruta);
	}

	@Override
	public void onFotoSeleccionada(File imagen) {
		String ruta = modelo.guardarFoto(imagen);

		vista.mostrarImagen(ruta);
	}

}
