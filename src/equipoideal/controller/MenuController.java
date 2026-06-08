package equipoideal.controller;

import equipoideal.model.IncompatibleModel;
import equipoideal.model.PersonaModel;
import equipoideal.model.RequerimientoModel;
import equipoideal.model.listener.IMenuListener;
import equipoideal.view.MenuView;
import equipoideal.view.dialogs.IncompatibleDialog;
import equipoideal.view.dialogs.PersonaDialog;
import equipoideal.view.dialogs.RequerimientoDialog;

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

	// TODO Eliseo, veo que se agregaron los controllers de personas, requerimientos
	// e incompatibilidades al constructor del NavigationController, pero no se
	// están utilizando en ningún lado, revisar si es necesario pasarlos o si se
	// pueden eliminar del constructor para simplificarlo
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
		if (this.personaModel.estaVacia()) {
			this.menuView.mostrarMensajeAdvertencia(
					"Debe cargar personas en el sistema antes de registrar incompatibilidades.");
			return;

		}

		incompatibleDialog.setVisible(true);
	}

	@Override
	public void onBusqueda() {
		if (this.personaModel.estaVacia()) {
			this.menuView
					.mostrarMensajeAdvertencia("Debe cargar personas en el sistema antes de buscar el equipo ideal.");
			return;
		}
		

		// TODO faltaria validar requerimientos

		this.navigationController.eventSearch();
	}

	public void dispose() {
		this.menuView.obtenerObserver().removeObserver(this);
	}
}
