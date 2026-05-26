package equipoideal.model;

import equipoideal.model.event.IObserverHeuristica;
import equipoideal.util.IndexCache;
import equipoideal.util.Observable;
import equipoideal.util.SolutionValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CalculadorHeuristica extends Observable<IObserverHeuristica> {

	private List<Persona> listaPersonas;
	private Map<String, Integer> requerimientos;
	private boolean[][] matrizIncompatibilidades;
	private List<Persona> listaOrdenadaPersonas;
	private IndexCache cacheIndice;

	public CalculadorHeuristica(List<Persona> listaPersonas, Map<String, Integer> requerimientos,
			boolean[][] matrizIncompatibilidades) {
		try {
			SolutionValidator.solutionValidator(listaPersonas, requerimientos, matrizIncompatibilidades);
		} catch (IllegalArgumentException e) {
			notifyObservers(o -> o.onError(e.getMessage()));
			return;
		}
		this.listaPersonas = new ArrayList<>(listaPersonas);
		this.listaOrdenadaPersonas = new ArrayList<>(listaPersonas);
		this.requerimientos = requerimientos;
		this.matrizIncompatibilidades = matrizIncompatibilidades;
		this.cacheIndice = new IndexCache(listaPersonas);
	}

	public void ejecutarHeuristica() {
		Equipo equipoResultante = new Equipo(new ArrayList<Persona>());
		// TODO: Verificar que el sort es DESCENDENTE (mayores calificaciones primero). Revisar si necesita Collections.reverseOrder()
		Collections.sort(listaOrdenadaPersonas, (persona, personaAComparar) -> {return persona.compareTo(personaAComparar);});
		try {
			for (Persona personaActual : listaOrdenadaPersonas) {
				if (esPosibleAgregar(personaActual, equipoResultante))
					equipoResultante.obtenerIntegrantes().add(personaActual);
				if (cumpleConTodosLosRequerimientos(equipoResultante)) {
					break;
				}
			}
			notifyObservers(o -> o.onHeuristicaSolved(equipoResultante.toDto()));
		} catch (Exception e) {
			notifyObservers(o -> o.onError(e.getMessage()));
		}
	}

	private boolean esPosibleAgregar(Persona persona, Equipo equipoParcial) {
		String rol = persona.getRol();
		int cantidadActualDelRol = equipoParcial.getCantidadPorRol(rol);
		int maximoRequerido = requerimientos.getOrDefault(rol, 0);

		return cantidadActualDelRol >= maximoRequerido || esIncompatibleConEquipo(persona, equipoParcial);
	}

	private boolean esIncompatibleConEquipo(Persona personaActual, Equipo equipoParcial) {
		int idxActual = listaPersonas.indexOf(personaActual);
		boolean algunoIncompatible = false;
		for (Persona integrante : equipoParcial.obtenerIntegrantes()) {
			int idxIntegrante = this.cacheIndice.obtenerIndiceCache().get(integrante);
			algunoIncompatible = algunoIncompatible || matrizIncompatibilidades[idxActual][idxIntegrante];
		}
		return algunoIncompatible;
	}

	private boolean cumpleConTodosLosRequerimientos(Equipo equipo) {
		for (Map.Entry<String, Integer> entry : requerimientos.entrySet()) {
			String rol = entry.getKey();
			int requerido = entry.getValue();
			if (equipo.getCantidadPorRol(rol) < requerido) {
				return false;
			}
		}
		return true;
	}
}
