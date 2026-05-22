package equipoideal.controller;

import equipoideal.model.Navigation;
import equipoideal.model.event.IObserverNavigation;
import equipoideal.util.VentanaEnum;
import equipoideal.view.MainView;

public class NavigationController implements IObserverNavigation {
	private MainView mainView;
	private Navigation navigation;
	
	public NavigationController(MainView mainView, Navigation navigation) {
		this.mainView = mainView;
		this.navigation = navigation;
		
		this.navigation.addObserver(this);
	}
	
	@Override
	public void onViewChanged(VentanaEnum viewName) {
		this.mainView.navegarA(viewName.toString());
		switch (viewName) {
		case MENU:
			//Aca Inicializo el MenuController
			break;
		}
		// Actualizar las vistas y crear los Controllers segun los casos.
	}
}
