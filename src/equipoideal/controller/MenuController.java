package equipoideal.controller;

import equipoideal.model.IncompatibleModel;
import equipoideal.model.PersonaModel;
import equipoideal.model.RequerimientoModel;
import equipoideal.model.listener.IMenuListener;
import equipoideal.view.MenuView;
import equipoideal.view.dialogs.IncompatibleDialog;
import equipoideal.view.dialogs.PersonaDialog;
import equipoideal.view.dialogs.RequerimientoDialog;
import equipoideal.view.dialogs.VentanaEmergente;



public class MenuController implements IMenuListener {
	private NavigationController navigationController;
	private MenuView menuView;

	private PersonaDialog personaDialog;
	private RequerimientoDialog requerimientosDialog;
	private IncompatibleDialog incompatibleDialog;

	private IncompatibleController incompatibleController;

	private PersonaModel personaModel;
	private RequerimientoModel requerimientoModel;
	private IncompatibleModel incompatibleModel;

	public MenuController(NavigationController navigationController, MenuView menuView, PersonaDialog personaDialog,
			RequerimientoDialog requerimientosDialog, IncompatibleDialog incompatibleDialog,
			PersonaController personaController, RequerimientoController requerimientoController,
			IncompatibleController incompatibleController, PersonaModel personaModel,
			RequerimientoModel requerimientoModel, IncompatibleModel incompatibleModel) {
		this.navigationController = navigationController;
		this.menuView = menuView;
		this.personaDialog = personaDialog;
		this.requerimientosDialog = requerimientosDialog;
		this.incompatibleDialog = incompatibleDialog;

		this.personaModel = personaModel;
		this.requerimientoModel = requerimientoModel;
		this.incompatibleModel = incompatibleModel;
		this.incompatibleController = incompatibleController;

		this.menuView.obtenerObserver().addObserver(this);
	}
	
	public void setIncompatibleController(IncompatibleController incompatibleController) {
        this.incompatibleController = incompatibleController;
    }

	// TODO agregar los controllers de los dialogs a este controllers ya que es
	// donde más sentido tiene, los modelos deben ser inyectados desde el main y
	// pasados a los controllers de los dialogs, para que estos puedan actualizarse
	// entre sí
	@Override
	public void onCargarPersonas() {
		personaDialog.setVisible(true);
	}

	@Override
	public void onRequerimientos() {
		requerimientosDialog.setVisible(true);
	}

	@Override
	public void onIncompatibilidad() {
		// refresca la pantalla para que cargue los nuevos nombres si es que cargaron
		// personas
		// TODO revisar si se puede refrescar sin revisar el controller, o si el
		// controller es el encargado de refrescar la pantalla cada vez que se abre el
		// dialog, revisar si es necesario llamar a este metodo desde el menu cada vez
		// que se abre la pantalla de incompatibilidades o si se puede llamar solo desde
		// el controller de incompatibilidades cada vez que se registra una nueva
		// incompatibilidad o se carga una nueva persona
		if (this.incompatibleController != null) {
			this.incompatibleController.refrescarPantalla();
		}

		// TODO: Reemplazar la verificación de getListaPersonas().size() == 0 por un método de conveniencia en el modelo como tienePersonas().
		if (this.personaModel.getListaPersonas() == null || this.personaModel.getListaPersonas().size() == 0) {
			this.menuView.mostrarMensajeAdvertencia(
					"Debe cargar personas en el sistema antes de registrar incompatibilidades.");
			return;
		}

		incompatibleDialog.setVisible(true);
	}

	// TODO si no hay personas, requerimientos o incompatibilidades cargados, debe
	// estar bloqueada la opción de búsqueda, y mostrar un mensaje indicando que se
	// deben cargar los datos primero
	@Override
	public void onBusqueda() {
		this.navigationController.eventSearch();
	}

	public void dispose() {
		this.menuView.obtenerObserver().removeObserver(this);
	}
}
