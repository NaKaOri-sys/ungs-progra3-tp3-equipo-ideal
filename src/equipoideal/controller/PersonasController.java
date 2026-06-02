package equipoideal.controller;

import java.io.File;

import equipoideal.model.PersonaDialogModel;
import equipoideal.model.listener.PersonasListener;
import equipoideal.util.RolEnum;
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
		  try {
	    String nombre = vista.getNombre();
	    String apellido = vista.getApellido();
	    int puntos = vista.getPuntos();
	    RolEnum rol = vista.getRol();
	    String ruta = vista.getRutaFoto();
	    
		modelo.agregarPersona(nombre, apellido, puntos, rol, ruta);
		
		  } catch (IllegalArgumentException e) {

		        System.out.println(e.getMessage());
		    }
	}

	@Override
	public void onCargaDesdeJson(String ruta) {
		
		try {
	    modelo.cargarDesdeJSON(ruta);
		} catch (IllegalArgumentException e) {

	        System.out.println(e.getMessage());
	    }
	}

	@Override
	public void onFotoSeleccionada(File imagen) {
		try {
	        
	        if (imagen == null || !imagen.exists() || imagen.isDirectory()) {
	            throw new IllegalArgumentException("El archivo seleccionado no es válido o no existe.");
	        }
	        
		String ruta = modelo.guardarFoto(imagen);

		vista.mostrarImagen(ruta);
		
		} catch (IllegalArgumentException e) {
//			vista.mostrarError
	        System.out.println(e.getMessage());
	    }
	}

	@Override
	public void onExportarJson(String ruta) {

	    modelo.exportarJson(ruta);
	}

	@Override
	public void onLimpiarCache() {
		modelo.limpiarCacheFotos();
//		vista.mostrarError(String "el cache se elimino");
	}

}
