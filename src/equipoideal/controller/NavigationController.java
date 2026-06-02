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
			// DATOS HARDCODEADOS PARA TESTING - DATASET GRANDE
			List<Persona> personasTest = new ArrayList<>();
			String[] nombres = {"Leo", "Cristiano", "Kylian", "Harry", "David", "Sergio", "Luis", "Gerard", "Xavi", "Andres", "Zinedine", "Ronaldinho", "Pele", "Maradona", "Beckham", "Zidane", "Figo", "Rivas", "Iniesta", "Maldini", "Buffon", "Van Der Sar", "Schmeichel", "Casillas", "Neuer", "Donnarumma", "Higuain", "Benzema", "Lewandowski", "Vardy"};
			String[] apellidos = {"Messi", "Ronaldo", "Mbappe", "Maguire", "De Gea", "Busquets", "Suarez", "Pique", "Hernandez", "Iniesta", "Zidane", "Ronaldinho", "Pele", "Maradona", "Beckham", "Van Nistelrooy", "Figo", "Rivas", "Carvajal", "Ramos", "Buffon", "Van Der Sar", "Schmeichel", "Casillas", "Neuer", "Donnarumma", "Higuain", "Benzema", "Lewandowski", "Vardy"};
			String[] roles = {"LIDER", "PROGRAMADOR", "TESTER", "ARQUITECTO", "LIDER", "PROGRAMADOR", "TESTER", "ARQUITECTO", "LIDER", "PROGRAMADOR", "TESTER", "ARQUITECTO", "LIDER", "PROGRAMADOR", "TESTER", "ARQUITECTO", "LIDER", "PROGRAMADOR", "TESTER", "ARQUITECTO", "LIDER", "PROGRAMADOR", "TESTER", "ARQUITECTO", "LIDER", "PROGRAMADOR", "TESTER", "ARQUITECTO", "LIDER", "PROGRAMADOR"};
			
			for (int i = 0; i < nombres.length; i++) {
				int calificacion = 2 + (i % 4); // Calificaciones de 2-5
				personasTest.add(new Persona(nombres[i], apellidos[i], calificacion, roles[i]));
			}
			
			List<Requerimiento> requerimientosTest = new ArrayList<>();
			requerimientosTest.add(new equipoideal.model.Requerimiento(equipoideal.util.RolEnum.LIDER, 2));
			requerimientosTest.add(new equipoideal.model.Requerimiento(equipoideal.util.RolEnum.ARQUITECTO, 2));
			requerimientosTest.add(new equipoideal.model.Requerimiento(equipoideal.util.RolEnum.PROGRAMADOR, 4));
			requerimientosTest.add(new equipoideal.model.Requerimiento(equipoideal.util.RolEnum.TESTER, 2));
			
			boolean[][] matrizTest = new boolean[personasTest.size()][personasTest.size()];
			// Algunas incompatibilidades aleatorias
			matrizTest[0][1] = true;
			matrizTest[1][0] = true;
			matrizTest[2][5] = true;
			matrizTest[5][2] = true;
			
			CalculadorBacktracking backtracking = new CalculadorBacktracking(personasTest,
					requerimientosTest, matrizTest);
			CalculadorHeuristica heuristica = new CalculadorHeuristica(personasTest,
					requerimientosTest, matrizTest);

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
