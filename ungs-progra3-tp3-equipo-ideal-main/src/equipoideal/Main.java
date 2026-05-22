
package equipoideal;

import javax.swing.JFrame;

import equipoideal.controller.PersonaIntegrationController;
import equipoideal.controller.PersonasController;
import equipoideal.model.PersonaDialogModel;
import model.repository.PersonaRepository;
import model.repository.PersonaRepositoryJson;
import view.dialogs.IncompatibleDialog;
import view.dialogs.PersonasDialog;
import view.dialogs.RequerimientosDialog;

public class Main {
	private static final String FILE_PATH = "personas.json";

	public static void main(String[] args) {
		JFrame frame = new JFrame();

		PersonaRepository repository = new PersonaRepositoryJson(FILE_PATH);
		PersonaDialogModel modeloPer = new PersonaDialogModel(repository);

//		IncompatibleDialog vistaPer = new IncompatibleDialog(frame, "IncompatibleDialog");       //Para visualizar la ventana
//		RequerimientosDialog vistaPer = new RequerimientosDialog(frame, "RequerimientosDialog");
		PersonasDialog vistaPer = new PersonasDialog(frame, "PersonasDialog");
		vistaPer.crearInputs(); 


		
		PersonasController controlPer = new PersonasController(vistaPer, modeloPer);
		PersonaIntegrationController controlInte = new PersonaIntegrationController(vistaPer, modeloPer);
		
		
		vistaPer.setVisible(true);
	}
}
