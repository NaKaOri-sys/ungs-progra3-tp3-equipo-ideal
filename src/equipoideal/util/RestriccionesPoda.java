package equipoideal.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import equipoideal.model.Equipo;
import equipoideal.model.Persona;

/**
 * Clase utilitaria donde se aplican las restricciones para el backtracking.
 */
public class RestriccionesPoda {
	public static boolean debePodar(Equipo solucionParcial, int indice, Equipo mejorEquipo, List<Persona> personas,
			Map<RolEnum, Integer> requerimientos) {
		if (mejorEquipo.obtenerIntegrantes().isEmpty()) {
			return false;
		}
		int puntajePosible = solucionParcial.getCalificacionTotal()
				+ calcularRemanenteMaximo(indice, personas, solucionParcial, requerimientos);
		return puntajePosible <= mejorEquipo.getCalificacionTotal();
	}

	public static int calcularRemanenteMaximo(int indice, List<Persona> listaPersonas, Equipo solucionParcial,
			Map<RolEnum, Integer> requerimientos) {
		Map<RolEnum, Integer> cuposRestantes = new HashMap<>();
		for (RolEnum rol : RolEnum.values()) {
			int req = requerimientos.getOrDefault(rol, 0);
			int actual = solucionParcial.getCantidadPorRol(rol);
			cuposRestantes.put(rol, req - actual);
		}

		int suma = 0;
		for (int i = indice; i < listaPersonas.size(); i++) {
			Persona p = listaPersonas.get(i);
			RolEnum rol = p.getRol();
			int cupo = cuposRestantes.getOrDefault(rol, 0);
			if (cupo > 0) {
				suma += p.getCalificacion();
				cuposRestantes.put(rol, cupo - 1);
			}
		}
		return suma;
	}
}
