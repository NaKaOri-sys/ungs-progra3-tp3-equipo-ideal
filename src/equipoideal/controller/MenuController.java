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

	private PersonaModel personaModel;
	private RequerimientoModel requerimientoModel;
	private IncompatibleModel incompatibleModel;
	private IncompatibleController incompatibleController;

	public MenuController(NavigationController navigationController, MenuView menuView, PersonaDialog personaDialog,
			RequerimientoDialog requerimientosDialog, IncompatibleDialog incompatibleDialog, PersonaModel personaModel,
			RequerimientoModel requerimientoModel, IncompatibleModel incompatibleModel,
			IncompatibleController incompatibleController) {
		this.navigationController = navigationController;
		this.menuView = menuView;
		this.personaDialog = personaDialog;
		this.requerimientosDialog = requerimientosDialog;
		this.incompatibleDialog = incompatibleDialog;
		this.incompatibleModel = incompatibleModel;
		this.personaModel = personaModel;
		this.requerimientoModel = requerimientoModel;
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
		this.incompatibleModel.sincronizarPersonasEnMapa(personaModel.getListaPersonas());
		this.incompatibleController.sincronizarDatos();
		incompatibleDialog.setVisible(true);
	}

	@Override
	public void onBusqueda() {
		if (this.personaModel.estaVacia()) {
			this.menuView
					.mostrarMensajeAdvertencia("Debe cargar personas en el sistema antes de buscar el equipo ideal.");
			return;
		}

		if (!this.requerimientoModel.hayRequerimientosCargados()) {
			this.menuView.mostrarMensajeAdvertencia(
					"Debe cargar requerimientos en el sistema antes de buscar el equipo ideal.");
			return;
		}

		this.navigationController.eventSearch();
	}

	public void dispose() {
		this.menuView.obtenerObserver().removeObserver(this);
	}
}
