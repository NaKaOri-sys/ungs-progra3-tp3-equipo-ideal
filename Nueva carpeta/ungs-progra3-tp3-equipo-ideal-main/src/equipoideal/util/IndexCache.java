package equipoideal.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import equipoideal.model.Persona;

public class IndexCache {
	private Map<Persona, Integer> indiceCache = new HashMap<>();

	public IndexCache(List<Persona> personas) {
		if (personas == null || personas.isEmpty())
			throw new IllegalArgumentException("La lista de personas, no puede estar vacia.");
		inicializarCache(personas);
	}

	private void inicializarCache(List<Persona> personas) {
		indiceCache.clear();
		for (int i = 0; i < personas.size(); i++) {
			indiceCache.put(personas.get(i), i);
		}
	}

	public Map<Persona, Integer> obtenerIndiceCache() {
		return this.indiceCache;
	}
}
