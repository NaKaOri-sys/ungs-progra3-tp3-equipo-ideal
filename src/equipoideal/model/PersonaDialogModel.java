package equipoideal.model;

import java.util.ArrayList;

import equipoideal.model.event.PersonasObserver;
import equipoideal.model.repository.PersonaRepository;
import equipoideal.util.Observable;

public class PersonaDialogModel extends Observable<PersonasObserver>{
	private ArrayList<Persona> listaPersonas;
	private PersonaRepository repository;
	
	public PersonaDialogModel(PersonaRepository repository)  {
		this.listaPersonas = new ArrayList<>();
		this.repository = repository;
	}
	
	public void agregarPersona(String nombre, String apellido, int puntos, String rol) {
        Persona p = new Persona(nombre, apellido, puntos, rol);
        listaPersonas.add(p);
        
        notifyObservers(observer -> observer.onListaPersonasModificada(new ArrayList<>(listaPersonas)));
	}

	public void guardarPersonaEnJSON() {
		repository.saveAll(listaPersonas);
	}
}
		
		
