package equipoideal;

import javax.swing.UIManager;

import equipoideal.controller.IncompatibleController;
import equipoideal.controller.NavigationController;
import equipoideal.controller.PersonaIntegrationController;
import equipoideal.controller.PersonasController;
import equipoideal.controller.RequerimientoIntegrationController;
import equipoideal.controller.RequerimientosController;
import equipoideal.model.CalculadorBacktracking;
import equipoideal.model.CalculadorHeuristica;
import equipoideal.model.CalculadorSolucion;
import equipoideal.model.IncompatibleModel;
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
		// TODO los nombres de los controladores, modelos y vistas, revisar si son
		// adecuados o si se pueden mejorar para que sean mas descriptivos

		// TODO si los controllers no se instancian en otro lugar, entonces hacer única
		// instancia por ej PersonaIntegracionController,
		// RequerimientoIntegrationController, IncompatibleController,
		// NavigationController, PersonasController, RequerimientosController

		PersonaRepository repository = new PersonaRepositoryJson(FILE_PATH);
		PersonaDialogModel personaDialogModel = new PersonaDialogModel(repository, FOLDER_PATH);
		// PERSONAS
		PersonasDialog personasDialog = new PersonasDialog(null, "PersonasDialog");
		personasDialog.crearInputs();
		PersonasController personaController = new PersonasController(personasDialog, personaDialogModel);
		PersonaIntegrationController personaIntegrationController = new PersonaIntegrationController(personasDialog,
				personaDialogModel);

		// REQUERIMIENTOS
		RequerimientosModel requerimientosModel = new RequerimientosModel(personaDialogModel);
		RequerimientosDialog requerimientosDialog = new RequerimientosDialog(null, "RequerimientosDialog");
		requerimientosDialog.crearInputs();
		RequerimientosController requerimientosController = new RequerimientosController(requerimientosDialog,
				requerimientosModel);
		RequerimientoIntegrationController reqIntegrationController = new RequerimientoIntegrationController(
				requerimientosDialog, personaDialogModel); // TODO este controller no tiene mucho sentido que guarde
															// personas, deberia simplemente cargar los requerimientos y
															// mostrarlos en la tabla que muestra las personas

		// INCOMPATIBILIDAD
		IncompatibleDialog incompatibleDialog = new IncompatibleDialog(null, "IncompatibleDialog");
		incompatibleDialog.crearInputs();

		IncompatibleModel incompatibleModel = new IncompatibleModel(personaDialogModel.getListaPersonas().size());
		IncompatibleController incompatibleController = new IncompatibleController(incompatibleDialog,
				personaDialogModel, incompatibleModel); // TODO este controller depende de que hayan personas cargadas,
														// por lo tanto hay que pasarle solamente la lista de personas,
														// no todo el modelo entero. Además la instancia de este mismo
														// deberia estar en NavigationController o en MenuController, sino nunca va a
														// actualizar la matriz y siempre será 0

		new NavigationController(mainView, navigation, personasDialog, personaDialogModel, requerimientosModel,
				requerimientosDialog, null, incompatibleDialog);
		navigation.updateView(VentanaEnum.MENU);

		mainView.setVisible(true);
	}

}