package equipoideal.model.repository;

import java.util.ArrayList;

import equipoideal.model.Persona;
import equipoideal.model.dao.PersonaArchivo;

public class PersonaRepositoryJson implements PersonaRepository{
	private String archivo;
	
	public PersonaRepositoryJson(String archivo) {
		this.archivo = archivo;
	}
	@Override
	public ArrayList<Persona> loadAll(String archivoJSON) {
	    return PersonaArchivo.cargarJSON(archivoJSON);
	}

	@Override
	public void saveAll(ArrayList<Persona> personas) {
		PersonaArchivo.generarJsonPersona(archivo, personas);
		
	}

	@Override
	public void exportarJson(String destino) {
		PersonaArchivo.exportarArchivoJSON(archivo, destino);
	}

}
