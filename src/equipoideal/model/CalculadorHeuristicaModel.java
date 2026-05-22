package equipoideal.model;

import equipoideal.model.event.IObserverHeuristica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CalculadorHeuristica implements IObserverHeuristica {

	private List<Persona> listaPersonas;
	private Map<String, Integer> requerimientos;
	private boolean[][] matrizIncompatibilidades;
	private List<Persona> listaOrdenadaPersonas;

	public CalculadorHeuristica(List<Persona> listaPersonas, Map<String, Integer> requerimientos,
			boolean[][] matrizIncompatibilidades) {
		this.listaPersonas = new ArrayList<>(listaPersonas);
		this.listaOrdenadaPersonas = new ArrayList<>(listaPersonas);
		this.requerimientos = requerimientos;
		this.matrizIncompatibilidades = matrizIncompatibilidades;
	}

	public Equipo ejecutarHeuristica() {
		Equipo equipoResultante = new Equipo();

		//TODO para que sea valido Persona, debe tener compareTo en la calificación
		Collections.sort(listaOrdenadaPersonas)
		for (Persona personaActual : listaOrdenadaPersonas) {
			if (esPosibleAgregar(personaActual, equipoResultante)) {
				equipoResultante.agregarJugador(personaActual);
			}

			if (cumpleConTodosLosRequerimientos(equipoResultante)) {
				break;
			}
		}

		return equipoResultante;
	}

	private boolean esPosibleAgregar(Persona persona, Equipo equipoParcial) {
		String rol = persona.getRol();
		boolean sePuedeAgregar = true;
		int cantidadActualDelRol = equipoParcial.getCantidadPorRol(rol);
		int maximoRequerido = requerimientos.getOrDefault(rol, 0);

		if (cantidadActualDelRol >= maximoRequerido) {
			return !sePuedeAgregar;
		}

		if (esIncompatibleConEquipo(persona, equipoParcial)) {
			return !sePuedeAgregar;
		}

		return sePuedeAgregar;
	}

	private boolean esIncompatibleConEquipo(Persona personaActual, Equipo equipoParcial) {
		int idxActual = listaPersonas.get(personaActual);
		boolean algunoIncompatible = false;
		for (Persona integrante : equipoParcial.getIntegrantes()) {
			int idxIntegrante = integrante.getId();
			algunoIncompatible = algunoIncompatible || matrizIncompatibilidades[idxActual][idxIntegrante] == true;
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
