package equipoideal.controller;

import java.io.File;

import equipoideal.model.PersonaDialogModel;
import equipoideal.model.listener.PersonasListener;
import equipoideal.util.RolEnum;
import equipoideal.view.dialogs.PersonasDialog;

public class PersonasController implements PersonasListener {

	private PersonasDialog vista;
	private PersonaDialogModel modelo;
	//TODO es un ABM de personas, pero no se pueden editar ni eliminar, solo agregar. Por lo menos el eliminar estaria bueno que este ya que si te equivocaste, tenes que cerrar la app.
	public PersonasController(PersonasDialog vista, PersonaDialogModel modelo) {
		this.vista = vista;
		this.modelo = modelo;
		this.vista.setPersonasListener(this);
	}

	// TODO se puede simplificar esto obteniendo directamente el dto Persona del
	// dialogo, y que el modelo se encargue de crear la persona a partir del dto,
	// validando los datos en el proceso. Esto reduciría la cantidad de parámetros
	// que se pasan entre el controlador y el modelo, y centralizaría la lógica de
	// creación de personas en un solo lugar.
	@Override
	public void onPersonaAgregada() {
		try {
			String nombre = vista.getNombre();
			String apellido = vista.getApellido();
			int puntos = vista.getPuntos();
			RolEnum rol = vista.getRol();
			String ruta = vista.getRutaFoto();

			modelo.agregarPersona(nombre, apellido, puntos, rol, ruta); //TODO se puede agregar un feedback que se agrego correctamente la persona.

		} catch (IllegalArgumentException e) {
			//TODO mostrar feedback de error en la vista.
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void onCargaDesdeJson(String ruta) {

		try {
			modelo.cargarDesdeJSON(ruta);
		} catch (IllegalArgumentException e) {
			//TODO mostrar feedback de error en la vista.
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
			//TODO mostrar feedback de error en la vista.
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
