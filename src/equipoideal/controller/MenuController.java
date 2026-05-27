package equipoideal.controller;

import equipoideal.model.listener.IMenuListener;
import equipoideal.view.MenuView;
import equipoideal.view.dialogs.IncompatibleDialog;
import equipoideal.view.dialogs.PersonasDialog;
import equipoideal.view.dialogs.RequerimientosDialog;

public class MenuController implements IMenuListener{
	private NavigationController navigationController;
	private MenuView menuView;
	private PersonasDialog personaDialog;
	private RequerimientosDialog requerimientosDialog;
	private IncompatibleDialog incompatibleDialog;
	
	public MenuController(NavigationController navigationController, MenuView menuView, PersonasDialog personaDialog, RequerimientosDialog requerimientosDialog, IncompatibleDialog incompatibleDialog) {
		this.navigationController = navigationController;
		this.menuView = menuView;
		this.personaDialog = personaDialog;
		this.requerimientosDialog = requerimientosDialog;
		this.incompatibleDialog = incompatibleDialog;
		
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
		incompatibleDialog.setVisible(true);
	}

	@Override
	public void onBusqueda() {	
		this.navigationController.eventSearch();
	}
	
	public void dispose() {
		this.menuView.obtenerObserver().removeObserver(this);
	}
}
