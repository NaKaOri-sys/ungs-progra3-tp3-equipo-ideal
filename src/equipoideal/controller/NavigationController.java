package equipoideal.controller;

import equipoideal.model.CalculadorSolucion;
import equipoideal.model.IncompatibleModel;
import equipoideal.model.Navigation;
import equipoideal.model.PersonaModel;
import equipoideal.model.RequerimientoModel;
import equipoideal.model.SolucionWorkerModel;
import equipoideal.model.dto.ResultadoComparativoDto;
import equipoideal.model.event.IObserverNavigation;
import equipoideal.util.VentanaEnum;
import equipoideal.view.MainView;
import equipoideal.view.dialogs.IncompatibleDialog;
import equipoideal.view.dialogs.PersonaDialog;
import equipoideal.view.dialogs.RequerimientoDialog;

public class NavigationController implements IObserverNavigation {

	private MainView mainView;
	private Navigation navigation;

	private PersonaDialog personasDialog;
	private RequerimientoDialog requerimientosDialog;
	private IncompatibleDialog incompatibleDialog;

	private PersonaModel personaModel;
	private RequerimientoModel requerimientoModel;
	private IncompatibleModel incompatibleModel;

	private MenuController menuController;
	private SolucionWorkerController solucionWorkerController;
	private WorkerResultController workerResultController;

	private PersonaController personaController;
	private RequerimientoController requerimientoController;

	private ResultadoComparativoDto resultadoComparativoDto;
	private IncompatibleController incompatibleController;
	private IncompatibleIntegrationController incompatibleIntegrationController;

	public NavigationController(MainView mainView, Navigation navigation, PersonaDialog personasDialog,
			RequerimientoDialog requerimientosDialog, IncompatibleDialog incompatibleDialog, PersonaModel personaModel,
			RequerimientoModel requerimientoModel, IncompatibleModel incompatibleModel,
			PersonaController personaController, RequerimientoController requerimientoController,
			IncompatibleController incompatibleController,
			IncompatibleIntegrationController incompatibleIntegrationController) {
		this.mainView = mainView;
		this.navigation = navigation;
		this.personasDialog = personasDialog;
		this.requerimientosDialog = requerimientosDialog;
		this.incompatibleDialog = incompatibleDialog;

		this.personaModel = personaModel;
		this.requerimientoModel = requerimientoModel;
		this.incompatibleModel = incompatibleModel;

		this.personaController = personaController;
		this.requerimientoController = requerimientoController;

		this.incompatibleController = incompatibleController;
		this.incompatibleIntegrationController = incompatibleIntegrationController;

		this.resultadoComparativoDto = new ResultadoComparativoDto();
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
			this.menuController = new MenuController(this, this.mainView.getPanelMenu(), this.personasDialog,
					this.requerimientosDialog, this.incompatibleDialog, this.personaModel, this.requerimientoModel,
					this.incompatibleModel, this.incompatibleController);
			break;

		case BUSQUEDA:
			if (this.solucionWorkerController != null) {
				this.solucionWorkerController.dispose();
				this.solucionWorkerController = null;
			}
			// Sincroniza personas nuevas al mapa sin borrar incompatibilidades registradas
			this.incompatibleModel.sincronizarPersonasEnMapa(personaModel.getListaPersonas());

			CalculadorSolucion calculador = CalculadorSolucion.crearDesdeModelos(personaModel, requerimientoModel,
					incompatibleModel);
			SolucionWorkerModel workerModel = new SolucionWorkerModel(this.resultadoComparativoDto);

			this.solucionWorkerController = new SolucionWorkerController(calculador, this.mainView.getPanelBusqueda(),
					this.navigation, workerModel);
			this.solucionWorkerController.execute();
			this.resultadoComparativoDto = workerModel.getResultado();
			break;

		case RESULTADO:
			if (this.workerResultController != null) {
				this.workerResultController.dispose();
				this.workerResultController = null;
			}

			this.workerResultController = new WorkerResultController(this.mainView.getPanelResultado(), this.navigation,
					this.resultadoComparativoDto);
			break;
		}
	}
}
