package equipoideal.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import equipoideal.model.dto.PersonaDto;
import equipoideal.model.event.IObserverPersona;
import equipoideal.model.repository.PersonaRepository;
import equipoideal.util.Observable;
import equipoideal.util.PersonaValidator;

public class PersonaModel extends Observable<IObserverPersona> {
	private ArrayList<Persona> listaPersonas;
	private PersonaRepository repository;
	private String rutaCarpetaFoto;

	public PersonaModel(PersonaRepository repository, String rutaCarpetaFoto) {
		this.listaPersonas = new ArrayList<>();
		this.repository = repository;
		this.rutaCarpetaFoto = rutaCarpetaFoto;
	}

	public void agregarPersona(PersonaDto dto) {
		PersonaValidator.validarPersona(dto);
		
		Persona p = new Persona(dto.getNombre(), dto.getApellido(), dto.getCalificacion(), dto.getRol(), dto.getRutaFoto());
		
		if (listaPersonas.contains(p)) {
			 throw new IllegalArgumentException("Esta persona ya existe!");
        }
		
		listaPersonas.add(p);

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

//	public void guardarPersonaEnJSON() {
//		repository.saveAll(listaPersonas);
//	}

	public void cargarDesdeJSON(String ruta) {

		if (!ruta.endsWith(".json")) {
			throw new IllegalArgumentException("El archivo debe ser JSON.");
		}

		ArrayList<Persona> personasCargadas = repository.loadAll(ruta);
		
		for (Persona p : personasCargadas) {
			if (p.getRutaFoto() != null) {
				File fotoOriginal = new File(p.getRutaFoto());
				if (fotoOriginal.exists()) {
					String nuevaRuta = guardarFoto(fotoOriginal);
					p.setRutaFoto(nuevaRuta);
				}
			}
			if (!listaPersonas.contains(p)) {
				listaPersonas.add(p);
			}
		}

		Collections.sort(listaPersonas, Collections.reverseOrder());
		notifyObservers(observer -> observer.onListaPersonasModificada(new ArrayList<>(listaPersonas)));
	}

	public void exportarJson(String destino) {
	    repository.exportarJson(destino, listaPersonas);
	}


	public void eliminarPersona(int fila) {
		if (fila < 0 || fila >= listaPersonas.size()) {
			throw new IllegalArgumentException("Indice inválido.");
		}

		listaPersonas.remove(fila);

		notifyObservers(observer ->observer.onListaPersonasModificada(new ArrayList<>(listaPersonas)));
	}

	public void editarPersona(int fila, PersonaDto dto) {

		if (fila < 0 || fila >= listaPersonas.size()) {
			throw new IllegalArgumentException("Indice inválido.");
		}

		PersonaValidator.validarPersona(dto);

		Persona personaEditada = new Persona(dto.getNombre(), dto.getApellido(), dto.getCalificacion(), dto.getRol(), dto.getRutaFoto());
		
		
		int indiceExistente = listaPersonas.indexOf(personaEditada);
		
		if (indiceExistente != -1 && indiceExistente != fila) {
			throw new IllegalArgumentException("Ya existe otra persona registrada con ese nombre y apellido.");
		}
		
		listaPersonas.set(fila, personaEditada);

		Collections.sort(listaPersonas, Collections.reverseOrder());

		notifyObservers(observer -> observer.onListaPersonasModificada(new ArrayList<>(listaPersonas)));
	}
	

	
	public ArrayList<Persona> getListaPersonas() {
		return listaPersonas;
	}
	
	public int getCantidadDePersonas() {
		return listaPersonas.size();
	}
	
	public boolean tienePersonas() {
	    return !listaPersonas.isEmpty();
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
	
	public List<String> obtenerNombresFormateados() {
        List<String> nombres = new ArrayList<>();
        for (Persona empleado : this.listaPersonas) {
            nombres.add(empleado.getNombre() + " " + empleado.getApellido() + " (" + empleado.getRol() + ")");
        }
        return nombres;
    }
	
	public boolean estaVacia() {
	    return this.listaPersonas.isEmpty();
	}
	
}
		
		
