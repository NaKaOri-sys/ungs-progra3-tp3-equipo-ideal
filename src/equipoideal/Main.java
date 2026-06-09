package equipoideal;

import javax.swing.UIManager;

import equipoideal.controller.IncompatibleController;
import equipoideal.controller.IncompatibleIntegrationController;
import equipoideal.controller.NavigationController;
import equipoideal.controller.PersonaController;
import equipoideal.controller.PersonaIntegrationController;
import equipoideal.controller.RequerimientoController;
import equipoideal.controller.RequerimientoIntegrationController;
import equipoideal.model.CalculadorBacktracking;
import equipoideal.model.CalculadorHeuristica;
import equipoideal.model.CalculadorSolucion;
import equipoideal.model.IncompatibleModel;
import equipoideal.model.Navigation;
import equipoideal.model.PersonaModel;
import equipoideal.model.RequerimientoModel;
import equipoideal.model.dto.ResultadoComparativoDto;
import equipoideal.model.repository.PersonaRepository;
import equipoideal.model.repository.PersonaRepositoryJson;
import equipoideal.util.VentanaEnum;
import equipoideal.view.MainView;
import equipoideal.view.dialogs.IncompatibleDialog;
import equipoideal.view.dialogs.PersonaDialog;
import equipoideal.view.dialogs.RequerimientoDialog;

public class Main {
	private static final String FILE_PATH = "personas.json";

	private static final String FOLDER_PATH = "data/fotos";

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		MainView mainView = new MainView();
		Navigation navigation = new Navigation();

		PersonaRepository repository = new PersonaRepositoryJson(FILE_PATH);
		PersonaModel personaDialogModel = new PersonaModel(repository, FOLDER_PATH);
		// PERSONAS
		PersonaDialog personasDialog = new PersonaDialog("Gestion de Personas");
		PersonaController personaController = new PersonaController(personasDialog, personaDialogModel);
		PersonaIntegrationController personaIntegrationController = new PersonaIntegrationController(personasDialog,
				personaDialogModel);

		// REQUERIMIENTOS
		RequerimientoModel requerimientosModel = new RequerimientoModel();
		RequerimientoDialog requerimientosDialog = new RequerimientoDialog("Gestion de Requerimientos");
		RequerimientoController requerimientosController = new RequerimientoController(requerimientosDialog,
				requerimientosModel);
		requerimientosController.setListaPersonas(personaDialogModel.getListaPersonas());
		RequerimientoIntegrationController reqIntegrationController = new RequerimientoIntegrationController(
				requerimientosDialog, requerimientosModel);

		// INCOMPATIBILIDAD
		IncompatibleDialog incompatibleDialog = new IncompatibleDialog("Gestion de Incompatibilidades");
		IncompatibleModel incompatibleModel = new IncompatibleModel();
		
		new NavigationController(mainView, navigation, personasDialog, requerimientosDialog, incompatibleDialog,
				personaDialogModel, requerimientosModel, incompatibleModel, personaController, requerimientosController);
		navigation.updateView(VentanaEnum.MENU);
		mainView.setVisible(true);
	}

}