package equipoideal.util;

import java.util.List;
import java.util.Map;

import equipoideal.model.Equipo;
import equipoideal.model.Persona;

/**
 * Clase utilitaria donde se aplican las restricciones para el backtracking.
 */
public class RestriccionesPoda {
	public static boolean debePodar(Equipo solucionParcial, int indice, Equipo mejorEquipo, List<Persona> personas) {
		int puntajePosible = solucionParcial.getCalificacionTotal() + calcularRemanenteMaximo(indice, personas);
		return puntajePosible <= mejorEquipo.getCalificacionTotal();
	}

	public static int calcularRemanenteMaximo(int indice, List<Persona> listaPersonas) {
		int suma = 0;
		for (int i = indice; i < listaPersonas.size(); i++) {
			suma += listaPersonas.get(i).getPuntos();
		}
		return suma;
	}

	public static boolean esIncompatibleConEquipo(int indicePersona, Equipo solucionMomentanea,
			List<Persona> listaPersonas, boolean[][] matrizIncompatibilidades, Map<Persona, Integer> indiceCache) {
		boolean esIncompatible = false;
		for (Persona integrante : solucionMomentanea.obtenerIntegrantes()) {
			Integer indiceIntegrante = indiceCache.get(integrante);
			if (indiceIntegrante != null) {
				esIncompatible = esIncompatible || matrizIncompatibilidades[indicePersona][indiceIntegrante];
			}
		}
		return esIncompatible;
	}
}
