package equipoideal;

import javax.swing.UIManager;

import equipoideal.controller.NavigationController;
import equipoideal.controller.PersonaIntegrationController;
import equipoideal.controller.PersonasController;
import equipoideal.controller.RequerimientoIntegrationController;
import equipoideal.controller.RequerimientosController;
import equipoideal.model.CalculadorBacktracking;
import equipoideal.model.CalculadorHeuristica;
import equipoideal.model.CalculadorSolucion;
import equipoideal.model.Navigation;
import equipoideal.model.PersonaDialogModel;
import equipoideal.model.RequerimientosModel;
import equipoideal.model.dto.ResultadoComparativoDto;
import equipoideal.model.repository.PersonaRepository;
import equipoideal.model.repository.PersonaRepositoryJson;
import equipoideal.util.VentanaEnum;
import equipoideal.view.MainView;
import equipoideal.view.dialogs.IncompatibleDialog;
import equipoideal.view.dialogs.PersonasDialog;
import equipoideal.view.dialogs.RequerimientosDialog;

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
		PersonaDialogModel personaDialogModel = new PersonaDialogModel(repository, FOLDER_PATH);
		// PERSONAS
		//TODO revisar nombres!!
		PersonasDialog personasDialog = new PersonasDialog(null,"PersonasDialog");
		personasDialog.crearInputs();
		PersonasController personaController = new PersonasController(personasDialog, personaDialogModel);
		PersonaIntegrationController personaIntegrationController = new PersonaIntegrationController(personasDialog, personaDialogModel);
		
		// REQUERIMIENTOS
		//TODO revisar nombres!!
		RequerimientosModel requerimientosModel = new RequerimientosModel(personaDialogModel);
		RequerimientosDialog requerimientosDialog = new RequerimientosDialog(null,"RequerimientosDialog");
		requerimientosDialog.crearInputs();
		RequerimientosController requerimientosController = new RequerimientosController(requerimientosDialog, requerimientosModel);
		RequerimientoIntegrationController reqIntegrationController = new RequerimientoIntegrationController(requerimientosDialog, personaDialogModel);

		
		// INCOMPATIBILIDAD
		//TODO los nombres!!!!
		IncompatibleDialog incompatibleDialog = new IncompatibleDialog(null,"IncompatibleDialog");
		incompatibleDialog.crearInputs();
		//TODO Falta crear el controller de incompatibilidad y el modelo de incompatibilidad
		
		new NavigationController(mainView, navigation,personasDialog,personaDialogModel, requerimientosModel, requerimientosDialog,null, incompatibleDialog);
		navigation.updateView(VentanaEnum.MENU);

		mainView.setVisible(true);
	}

}