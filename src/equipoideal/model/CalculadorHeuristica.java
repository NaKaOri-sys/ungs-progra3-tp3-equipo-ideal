package equipoideal.model;

import equipoideal.model.dto.EquipoDto;
import equipoideal.model.event.IObserverHeuristica;
import equipoideal.util.IndexCache;
import equipoideal.util.Observable;
import equipoideal.util.SolutionValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

public class CalculadorHeuristica {

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
			System.err.println("Hubo un error al inicializar la heuristica: "+e.getMessage());
			return;
		}
		this.listaPersonas = new ArrayList<>(listaPersonas);
		this.listaOrdenadaPersonas = new ArrayList<>(listaPersonas);
		this.requerimientos = requerimientos;
		this.matrizIncompatibilidades = matrizIncompatibilidades;
	}

	public EquipoDto ejecutarHeuristica() {
		this.cacheIndice = new IndexCache(listaPersonas);
		Equipo equipoResultante = new Equipo(new ArrayList<Persona>());
		Collections.sort(listaOrdenadaPersonas, (p1, p2) -> p2.compareTo(p1));
		try {
			for (Persona personaActual : listaOrdenadaPersonas) {
				if (esPosibleAgregar(personaActual, equipoResultante))
					equipoResultante.obtenerIntegrantes().add(personaActual);
				if (cumpleConTodosLosRequerimientos(equipoResultante)) {
					break;
				}
			}
			
			return equipoResultante.toDto();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private boolean esPosibleAgregar(Persona persona, Equipo equipoParcial) {
		String rol = persona.getRol();
		int cantidadActualDelRol = equipoParcial.getCantidadPorRol(rol);
		int maximoRequerido = requerimientos.getOrDefault(rol, 0);

		return !(cantidadActualDelRol >= maximoRequerido) && !(esIncompatibleConEquipo(persona, equipoParcial));
	}

	private boolean esIncompatibleConEquipo(Persona personaActual, Equipo equipoParcial) {
		int idxActual = this.cacheIndice.obtenerIndiceCache().get(personaActual);
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
