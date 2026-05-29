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
	public ArrayList<Persona> loadAll(String archivo1) {
	    return PersonaArchivo.cargarJSON(archivo1);
	}

	@Override
	public void saveAll(ArrayList<Persona> personas) {
		PersonaArchivo.generarJsonPersona(archivo, personas);
		
	}

	@Override
	public void cleanAll() {
		PersonaArchivo.limpiarArchivoJson(archivo);
	}

}
