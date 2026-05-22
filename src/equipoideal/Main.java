package equipoideal;

import javax.swing.UIManager;

import equipoideal.controller.NavigationController;
import equipoideal.model.Navigation;
import equipoideal.model.PersonaDialogModel;
import equipoideal.model.repository.PersonaRepository;
import equipoideal.model.repository.PersonaRepositoryJson;
import equipoideal.view.MainView;

public class Main {
	private static final String FILE_PATH = "personas.json";

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		MainView mainView = new MainView();
		Navigation navigation = new Navigation();
		PersonaRepository repository = new PersonaRepositoryJson(FILE_PATH);
		PersonaDialogModel modeloPer = new PersonaDialogModel(repository);

		//TODO ver como hacer q navigation muestre u oculte los dialog (quizas estos deban ir en otro lado inicializados
//		IncompatibleDialog vistaPer = new IncompatibleDialog(frame, "IncompatibleDialog");       //Para visualizar la ventana
//		RequerimientosDialog vistaPer = new RequerimientosDialog(frame, "RequerimientosDialog");
//		PersonasDialog vistaPer = new PersonasDialog(frame, "PersonasDialog");
//		vistaPer.crearInputs();

		new NavigationController(mainView, navigation);
		//TODO eliseo pls revisate esto
//		PersonasController controlPer = new PersonasController(vistaPer, modeloPer);
//		PersonaIntegrationController controlInte = new PersonaIntegrationController(vistaPer, modeloPer);

		mainView.setVisible(true);
	}

}
