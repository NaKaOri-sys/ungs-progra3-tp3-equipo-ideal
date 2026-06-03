package equipoideal.controller;

import java.util.ArrayList;
import java.util.List;

import equipoideal.model.CalculadorBacktracking;
import equipoideal.model.CalculadorHeuristica;
import equipoideal.model.CalculadorSolucion;
import equipoideal.model.IncompatibleModel;
import equipoideal.model.Navigation;
import equipoideal.model.Persona;
import equipoideal.model.PersonaDialogModel;
import equipoideal.model.Requerimiento;
import equipoideal.model.RequerimientosModel;
import equipoideal.model.dto.ResultadoComparativoDto;
import equipoideal.model.event.IObserverNavigation;
import equipoideal.util.RolEnum;
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

	private PersonaDialogModel personaModel;
	private RequerimientosModel requerimientoModel;
	private IncompatibleModel incompatibleModel;

	private MenuController menuController;
	private SolucionWorkerController solucionWorkerController;
	private WorkerResultController workerResultController;

	private ResultadoComparativoDto resultadoComparativoDto;

	public NavigationController(MainView mainView, Navigation navigation, PersonasDialog personasDialog,
			PersonaDialogModel personaModel, RequerimientosModel requerimientoModel,
			RequerimientosDialog requerimientosDialog, IncompatibleModel incompatibleModel,
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
					this.requerimientosDialog, this.incompatibleDialog);
			break;

		case BUSQUEDA:
			if (this.solucionWorkerController != null) {
				this.solucionWorkerController.dispose();
				this.solucionWorkerController = null;
			}
			
			//TODO cuando ya este incompatibilidades, se puede borrar esto
			boolean[][] matrizTest = new boolean[personaModel.getListaPersonas().size()][personaModel.getListaPersonas().size()];
			// Algunas incompatibilidades aleatorias
			matrizTest[0][1] = true;
			matrizTest[1][0] = true;
			matrizTest[2][5] = true;
			matrizTest[5][2] = true;
			
			CalculadorBacktracking backtracking = new CalculadorBacktracking(personaModel.getListaPersonas(),
					requerimientoModel.getRequerimientos(), matrizTest);
			CalculadorHeuristica heuristica = new CalculadorHeuristica(personaModel.getListaPersonas(),
					requerimientoModel.getRequerimientos(), matrizTest);

			CalculadorSolucion calculador = new CalculadorSolucion(backtracking, heuristica);
			this.resultadoComparativoDto = new equipoideal.model.dto.ResultadoComparativoDto();
			this.solucionWorkerController = new SolucionWorkerController(calculador,
					this.mainView.getPanelBusqueda(), this.navigation, this.resultadoComparativoDto);
			this.solucionWorkerController.execute();
			break;

		case RESULTADO:
			if (this.workerResultController != null) {
				this.workerResultController.dispose();
				this.workerResultController = null;
			}
			
			this.workerResultController = new WorkerResultController(this.mainView.getPanelResultado(),
					this.navigation, this.resultadoComparativoDto);
			break;
		}
		// TODO Actualizar las vistas y crear los Controllers segun los casos.
	}
}
