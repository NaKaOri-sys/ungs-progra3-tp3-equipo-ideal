package equipoideal.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import equipoideal.model.event.IObserverIncompatible;
import equipoideal.util.Observable;

public class IncompatibleModel extends Observable<IObserverIncompatible> {
	private HashMap<Persona, Set<Persona>> incompatibilidades;

	public IncompatibleModel() {
		this.incompatibilidades = new HashMap<Persona, Set<Persona>>();
	}

	public void crearMapIncompatibilidades(List<Persona> personas) {
		for (Persona persona : personas) {
			this.incompatibilidades.put(persona, new HashSet<Persona>());
		}
	}

	// Agrega al mapa solo las personas que aun no estan, sin borrar las incompatibilidades ya registradas
	public void sincronizarPersonasEnMapa(List<Persona> personas) {
		for (Persona persona : personas) {
			this.incompatibilidades.putIfAbsent(persona, new HashSet<Persona>());
		}
	}

	// Registra la incompatibilidad de forma directa usando los índices de la
	// pantalla.
	// es bidireccional, osea si A es incompatible con B, B lo es con A
	public void registrarIncompatibilidad(Persona persona, Persona incompatible) {
		if (!this.incompatibilidades.containsKey(persona) || !this.incompatibilidades.containsKey(incompatible))
			throw new IllegalArgumentException("Las personas a analizar no se encuentran registradas en el sistema.");

		this.incompatibilidades.get(persona).add(incompatible);
		this.incompatibilidades.get(incompatible).add(persona);
		notifyObservers(o -> o.alCrearIncompatibilidad(persona, incompatible));
	}

	public void eliminarIncompatibilidad(Persona persona, Persona incompatible) {
		if (!this.incompatibilidades.containsKey(persona) || !this.incompatibilidades.containsKey(incompatible))
			throw new IllegalArgumentException("Las personas a analizar no se encuentran registradas en el sistema.");
		this.incompatibilidades.get(persona).remove(incompatible);
		this.incompatibilidades.get(incompatible).remove(persona);
		notifyObservers(o -> o.alEliminarIncompatibilidad(persona, incompatible));
	}

	public boolean sonIncompatibles(Persona persona, Persona incompatible) {
		if (!this.incompatibilidades.containsKey(persona) || !this.incompatibilidades.containsKey(incompatible))
			throw new IllegalArgumentException("Las personas a analizar no se encuentran registradas en el sistema.");

		return this.incompatibilidades.get(persona).contains(incompatible)
				&& this.incompatibilidades.get(incompatible).contains(persona);
	}

	public Map<Persona, Set<Persona>> obtenerIncompatibilidades() {
		return this.incompatibilidades;
	}
	
	public boolean hayIncompatibilidades() {
		return this.incompatibilidades.size() > 0;
	}
}
