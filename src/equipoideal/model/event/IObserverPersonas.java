package equipoideal.model.event;

import java.util.ArrayList;
import equipoideal.model.Persona;

public interface IObserverPersonas {

	void onListaPersonasModificada(ArrayList<Persona> nuevaLista);

}
