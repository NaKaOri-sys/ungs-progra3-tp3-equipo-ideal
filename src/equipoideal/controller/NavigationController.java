package equipoideal.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import equipoideal.model.CalculadorBacktracking;
import equipoideal.model.CalculadorHeuristica;
import equipoideal.model.CalculadorSolucion;
import equipoideal.model.IncompatibleModel;
import equipoideal.model.Navigation;
import equipoideal.model.Persona;
import equipoideal.model.PersonaModel;
import equipoideal.model.RequerimientoModel;
import equipoideal.model.SolucionWorkerModel;
import equipoideal.model.dto.ResultadoComparativoDto;
import equipoideal.model.event.IObserverNavigation;
import equipoideal.util.RolEnum;
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
	
	private IncompatibleController incompatibleController;

	private ResultadoComparativoDto resultadoComparativoDto;

	public NavigationController(MainView mainView, Navigation navigation, PersonaDialog personasDialog,
			PersonaModel personaModel, RequerimientoModel requerimientoModel,
			RequerimientoDialog requerimientosDialog, IncompatibleModel incompatibleModel,
			IncompatibleDialog incompatibleDialog) {
		this.mainView = mainView;
		this.navigation = navigation;
		this.personasDialog = personasDialog;
		this.requerimientosDialog = requerimientosDialog;
		this.incompatibleDialog = incompatibleDialog;
		this.personaModel = personaModel;
		this.requerimientoModel = requerimientoModel;
		this.incompatibleModel = incompatibleModel;
		this.resultadoComparativoDto = new ResultadoComparativoDto();
		//TODO esto no va acá ya que acopla demasiado a navigation habria que inicializarlo en main o en menuController 
		this.incompatibleController = new IncompatibleController(this.incompatibleDialog, this.personaModel.getListaPersonas(), this.incompatibleModel);
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
			                    this.requerimientosDialog, this.incompatibleDialog, this.incompatibleController); 
		    
		    if (this.incompatibleController != null) {
		        this.incompatibleController.refrescarPantalla();
		    }
			break;

		case BUSQUEDA:
			if (this.solucionWorkerController != null) {
				this.solucionWorkerController.dispose();
				this.solucionWorkerController = null;
			}

			CalculadorBacktracking backtracking = new CalculadorBacktracking(
					new ArrayList<>(personaModel.getListaPersonas()),
					new LinkedHashMap<RolEnum, Integer>(requerimientoModel.getRequerimientos()), this.incompatibleModel.getMatrizIncompatibilidades());
			CalculadorHeuristica heuristica = new CalculadorHeuristica(new ArrayList<>(personaModel.getListaPersonas()),
					new LinkedHashMap<RolEnum, Integer>(requerimientoModel.getRequerimientos()), this.incompatibleModel.getMatrizIncompatibilidades());

			CalculadorSolucion calculador = new CalculadorSolucion(backtracking, heuristica);
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
		// TODO Actualizar las vistas y crear los Controllers segun los casos.
	}
}
