package equipoideal.controller;

import equipoideal.model.CalculadorBacktracking;
import equipoideal.model.Navigation;
import equipoideal.model.PersonaDialogModel;
import equipoideal.model.event.IObserverNavigation;
import equipoideal.util.VentanaEnum;
import equipoideal.view.MainView;

public class NavigationController implements IObserverNavigation {
	private MainView mainView;
	private Navigation navigation;
	private PersonaDialogModel personaDialogModel;
	
	private MenuController menuController;
	private SolucionWorkerController solucionWorkerController;
	
	public NavigationController(MainView mainView, Navigation navigation, PersonaDialogModel personaDialogModel) {
		this.mainView = mainView;
		this.navigation = navigation;
		this.personaDialogModel = personaDialogModel;
		
		this.navigation.addObserver(this);
	}
	
	public void eventSearch() {
		this.navigation.updateView(VentanaEnum.BUSQUEDA);
	}
	
	@Override
	public void onViewChanged(VentanaEnum viewName) {
		this.mainView.navegarA(viewName.toString());
		switch (viewName) {
		case MENU:
			if (this.menuController != null) {
				this.menuController.dispose();
				this.menuController = null;
			}
			this.menuController = new MenuController(this, this.mainView.getPanelMenu(),this.personaDialogModel);
			break;
		case BUSQUEDA:
			if (this.solucionWorkerController != null) {
				//this.solucionWorkerController.dispose(); // TODO Nahuel, en el controller del worker va el dispose() ?
				this.solucionWorkerController = null;
			}
			CalculadorBacktracking modelBacktracking = new CalculadorBacktracking();
			this.solucionWorkerController = new SolucionWorkerController(modelBacktracking, this.mainView.getPanelBusqueda()); //TODO pasarle el modelo del backtracking
			break;
		}
		// TODO Actualizar las vistas y crear los Controllers segun los casos.
	}
}
