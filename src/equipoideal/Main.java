package equipoideal;

import javax.swing.UIManager;

import equipoideal.controller.IncompatibleController;
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
		// TODO los nombres de los controladores, modelos y vistas, revisar si son
		// adecuados o si se pueden mejorar para que sean mas descriptivos

		// TODO si los controllers no se instancian en otro lugar, entonces hacer única
		// instancia por ej PersonaIntegracionController,
		// RequerimientoIntegrationController, IncompatibleController,
		// NavigationController, PersonasController, RequerimientosController

		PersonaRepository repository = new PersonaRepositoryJson(FILE_PATH);
		PersonaModel personaDialogModel = new PersonaModel(repository, FOLDER_PATH);
		// PERSONAS
		PersonaDialog personasDialog = new PersonaDialog("Gestion de Personas");
		personasDialog.crearInputs();
		PersonaController personaController = new PersonaController(personasDialog, personaDialogModel);
		PersonaIntegrationController personaIntegrationController = new PersonaIntegrationController(personasDialog,
				personaDialogModel);

		// REQUERIMIENTOS
		RequerimientoModel requerimientosModel = new RequerimientoModel(personaDialogModel);
		RequerimientoDialog requerimientosDialog = new RequerimientoDialog("Gestion de Requerimientos");
		requerimientosDialog.crearInputs();
		RequerimientoController requerimientosController = new RequerimientoController(requerimientosDialog, requerimientosModel);
		RequerimientoIntegrationController reqIntegrationController = new RequerimientoIntegrationController(
				requerimientosDialog, personaDialogModel);
		 // TODO este controller no tiene mucho sentido que guarde
		
		// personas, deberia simplemente cargar los requerimientos y
		// mostrarlos en la tabla que muestra las personas

		// INCOMPATIBILIDAD
		IncompatibleDialog incompatibleDialog = new IncompatibleDialog("Gestion de Incompatibilidades");
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