package equipoideal.model.repository;

import java.util.ArrayList;

import equipoideal.model.Persona;

public interface PersonaRepository {
	ArrayList<Persona> loadAll(String archivo);
	void exportarJson(String destino, ArrayList<Persona> personas);
}
