package equipoideal;

import javax.swing.UIManager;

import equipoideal.controller.NavigationController;
import equipoideal.model.Navigation;
import equipoideal.model.PersonaDialogModel;
import equipoideal.model.repository.PersonaRepository;
import equipoideal.model.repository.PersonaRepositoryJson;
import equipoideal.util.VentanaEnum;
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
		PersonaDialogModel personaDialogModel = new PersonaDialogModel(repository);

		//TODO Faltaria los models de los otros dialogs, pero por ahora solo el de personas puse en navigationController
		
		new NavigationController(mainView, navigation,personaDialogModel);
		navigation.updateView(VentanaEnum.MENU);

		mainView.setVisible(true);
	}

}
