package equipoideal.controller;

import equipoideal.model.CalculadorSolucion;
import equipoideal.model.Navigation;
import equipoideal.model.dto.ResultadoComparativoDto;
import equipoideal.model.event.IObserverNavigation;
import equipoideal.util.VentanaEnum;
import equipoideal.view.MainView;
import equipoideal.view.dialogs.IncompatibleDialog;
import equipoideal.view.dialogs.PersonasDialog;
import equipoideal.view.dialogs.RequerimientosDialog;

public class NavigationController implements IObserverNavigation {
	
	private MainView mainView;
	private Navigation navigation;
	
	private PersonasDialog personasDialog;
	private RequerimientosDialog requerimientosDialog;
	private IncompatibleDialog incompatibleDialog;
	
	private MenuController menuController;
	private SolucionWorkerController solucionWorkerController;
	private WorkerResultController workerResultController;
	
	private ResultadoComparativoDto resultadoComparativoDto;
	private CalculadorSolucion calculador;
	
	public NavigationController(MainView mainView, Navigation navigation, PersonasDialog personasDialog,
			RequerimientosDialog requerimientosDialog, IncompatibleDialog incompatibleDialog, CalculadorSolucion calculador) {
		this.mainView = mainView;
		this.navigation = navigation;
		this.personasDialog = personasDialog;
		this.requerimientosDialog = requerimientosDialog;
		this.incompatibleDialog = incompatibleDialog;
		this.calculador = calculador;
		
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
			this.menuController = new MenuController(this, this.mainView.getPanelMenu(),this.personasDialog,this.requerimientosDialog,this.incompatibleDialog);
			break;
			
		case BUSQUEDA:
			if (this.solucionWorkerController != null) {
				this.solucionWorkerController.dispose();
				this.solucionWorkerController = null;
			}

			this.solucionWorkerController = new SolucionWorkerController(this.calculador, this.mainView.getPanelBusqueda(), this.navigation,this.resultadoComparativoDto);
			break;
			
		case RESULTADO:
			if(this.workerResultController != null) {
				this.workerResultController.dispose();
				this.workerResultController = null;
			}
			this.workerResultController = new WorkerResultController(this.mainView.getPanelResultado(), this.navigation);
			break;
		}
		// TODO Actualizar las vistas y crear los Controllers segun los casos.
	}
}
