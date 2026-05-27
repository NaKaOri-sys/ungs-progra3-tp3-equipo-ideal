package equipoideal.controller;

import java.awt.event.ActionEvent;

import equipoideal.model.PersonaDialogModel;
import equipoideal.model.listener.IMenuListener;
import equipoideal.view.MenuView;
import equipoideal.view.dialogs.IncompatibleDialog;
import equipoideal.view.dialogs.PersonasDialog;
import equipoideal.view.dialogs.RequerimientosDialog;

public class MenuController implements IMenuListener{
	private NavigationController navigationController;
	private MenuView menuView;
	private PersonaDialogModel personaDialogModel;
	
	public MenuController(NavigationController navigationController, MenuView menuView,PersonaDialogModel personaDialogModel) {
		this.navigationController = navigationController;
		this.menuView = menuView;
		this.personaDialogModel = personaDialogModel;
		this.menuView.obtenerObserver().addObserver(this);
		
	}
	@Override
	public void onCargarPersonas() {
		PersonasDialog personaDialog = new PersonasDialog(null,"PersonasDialog");
		personaDialog.crearInputs();
		
		PersonasController personaController = new PersonasController(personaDialog, this.personaDialogModel);
		PersonaIntegrationController personaIntegrationController = new PersonaIntegrationController(personaDialog, this.personaDialogModel);
		personaDialog.setVisible(true);
	}

	@Override
	public void onRequerimientos() {
		RequerimientosDialog requerimientosDialog = new RequerimientosDialog(null,"RequerimientosDialog");
		requerimientosDialog.crearInputs();
		
		RequerimientosController requerimientosController = new RequerimientosController();
		
		requerimientosDialog.setVisible(true);
	}

	@Override
	public void onIncompatibilidad() {
		IncompatibleDialog incompatibleDialog = new IncompatibleDialog(null,"IncompatibleDialog");
		incompatibleDialog.crearInputs();
		//TODO faltaria su Controller
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
