package equipoideal.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import equipoideal.model.event.PersonasObserver;
import equipoideal.model.repository.PersonaRepository;
import equipoideal.util.Observable;

public class PersonaDialogModel extends Observable<PersonasObserver>{
	private ArrayList<Persona> listaPersonas;
	private PersonaRepository repository;
	
	public PersonaDialogModel(PersonaRepository repository)  {
		this.listaPersonas = new ArrayList<>();;
		this.repository = repository;
	}
	
	public void agregarPersona(String nombre, String apellido, int puntos, String rol, String ruta) {
        Persona p = new Persona(nombre, apellido, puntos, rol);
        listaPersonas.add(p);
        p.setRutaFoto(ruta);
        
        notifyObservers(observer -> observer.onListaPersonasModificada(new ArrayList<>(listaPersonas)));
	}

	public void guardarPersonaEnJSON() {
		repository.saveAll(listaPersonas);
	}
	
	public void cargarDesdeJSON(String ruta) {

	    ArrayList<Persona> personasCargadas = repository.loadAll(ruta);

	    listaPersonas.addAll(personasCargadas);

	    notifyObservers(observer -> observer.onListaPersonasModificada(new ArrayList<>(listaPersonas)));
	}

	public String guardarFoto(File imagen) {
		File carpeta = new File("data/fotos");
        carpeta.mkdirs();
        
        String nombreArchivo =System.currentTimeMillis()+ "_" + imagen.getName();
        
        File destino = new File(carpeta,nombreArchivo);
        
        try {
        	Files.copy(imagen.toPath(),destino.toPath(),StandardCopyOption.REPLACE_EXISTING );

	        return destino.getPath();
	        
        } catch (IOException e) {

	        throw new RuntimeException(e);
	    }
	}
}
		
		
