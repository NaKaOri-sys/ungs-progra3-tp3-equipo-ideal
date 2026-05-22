package view.dialog.event;

import java.util.ArrayList;
import equipoideal.model.Persona;

public interface PersonasObserver {

	void onListaPersonasModificada(ArrayList<Persona> nuevaLista);

}
