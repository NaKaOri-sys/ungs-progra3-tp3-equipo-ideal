package equipoideal.model.repository;

import java.util.ArrayList;

import equipoideal.model.Persona;
import equipoideal.model.dao.PersonaArchivo;

public class PersonaRepositoryJson implements PersonaRepository{
	
	public PersonaRepositoryJson() {
	}
	@Override
	public ArrayList<Persona> loadAll(String archivoJSON) {
	    return PersonaArchivo.cargarJSON(archivoJSON);
	}

	@Override
	public void exportarJson(String destino, ArrayList<Persona> personas) {
		PersonaArchivo.exportarArchivoJSON(destino, personas);
	}

}
