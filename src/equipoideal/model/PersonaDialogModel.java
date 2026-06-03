package equipoideal.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;

import equipoideal.model.event.IObserverPersonas;
import equipoideal.model.repository.PersonaRepository;
import equipoideal.util.Observable;
import equipoideal.util.PersonaValidator;
import equipoideal.util.RolEnum;

public class PersonaDialogModel extends Observable<IObserverPersonas>{
	private ArrayList<Persona> listaPersonas;
	private PersonaRepository repository;
	private String rutaCarpetaFoto;
	
	public PersonaDialogModel(PersonaRepository repository, String rutaCarpetaFoto)  {
		this.listaPersonas = new ArrayList<>();
		this.repository = repository;
		this.rutaCarpetaFoto = rutaCarpetaFoto;
	}
	
	public void agregarPersona(String nombre, String apellido, int puntos, RolEnum rol, String ruta) {
		PersonaValidator.validarPersona(nombre, apellido, puntos, rol);
		
        Persona p = new Persona(nombre, apellido, puntos, rol);
        listaPersonas.add(p);
        p.setRutaFoto(ruta);
        
        Collections.sort(listaPersonas, Collections.reverseOrder());
        notifyObservers(observer -> observer.onListaPersonasModificada(new ArrayList<>(listaPersonas)));
	}

	public void guardarPersonaEnJSON() {
		repository.saveAll(listaPersonas);
	}
	
	public void cargarDesdeJSON(String ruta) {
		
		if (!ruta.endsWith(".json")) {
		    throw new IllegalArgumentException("El archivo debe ser JSON.");
		}

	    ArrayList<Persona> personasCargadas = repository.loadAll(ruta);

	    listaPersonas.addAll(personasCargadas);
	    Collections.sort(listaPersonas, Collections.reverseOrder());
	    notifyObservers(observer -> observer.onListaPersonasModificada(new ArrayList<>(listaPersonas)));
	}

	public String guardarFoto(File imagen) {
		String nombre = imagen.getName().toLowerCase();
		
		if (!nombre.endsWith(".png") && !nombre.endsWith(".jpg") && !nombre.endsWith(".jpeg")) {
		    throw new IllegalArgumentException("El archivo debe ser PNG, JPG o JPEG.");
		}
		
		File carpeta = new File(rutaCarpetaFoto);
        carpeta.mkdirs();
        
        String nombreArchivo = System.currentTimeMillis()+ "_" + imagen.getName();
        
        File destino = new File(carpeta,nombreArchivo);
        
        try {
        	Files.copy(imagen.toPath(),destino.toPath(),StandardCopyOption.REPLACE_EXISTING );

	        return destino.getPath();
	        
        } catch (IOException e) {

	        throw new RuntimeException(e);
	    }
	}
	
	public ArrayList<Persona> getListaPersonas() {
	    return listaPersonas;
	}

	public void exportarJson(String destino) {

	    repository.exportarJson(destino);
	}

	public void limpiarCacheFotos() {
		File carpeta = new File(rutaCarpetaFoto);

	    if (!carpeta.exists()) {
	        return;
	    }

	    File[] archivos = carpeta.listFiles();

	    if (archivos == null) {
	        return;
	    }

	    for (File archivo : archivos) {

	        if (archivo.isFile()) {
	            archivo.delete();
	        }
	    }
		
	}
	
	
	
	public String obtenerNombrePorIndice(int indice) {
	    if (indice >= 0 && indice < listaPersonas.size()) {
	        Persona p = listaPersonas.get(indice);
	        return p.getNombre() + " " + p.getApellido();
	    }
	    return "";
	}
	
	
}
		
		
