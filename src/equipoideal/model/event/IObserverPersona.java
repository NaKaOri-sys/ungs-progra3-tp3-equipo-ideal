package equipoideal.model.event;

import java.util.ArrayList;
import equipoideal.model.Persona;

public interface IObserverPersona {
	void onListaPersonasModificada(ArrayList<Persona> nuevaLista);
}
