package equipoideal.util;

import java.util.Map;
import java.util.Map.Entry;

import equipoideal.model.Equipo;
import equipoideal.model.Persona;

public class EquipoCalculadorUtil {

	public static boolean cumpleConLosRequerimientos(Equipo equipo, Map<RolEnum, Integer> requerimientos) {
		for (Entry<RolEnum, Integer> req : requerimientos.entrySet()) {
			RolEnum rol = req.getKey();
			if (equipo.getCantidadPorRol(rol) != req.getValue()) {
				return false;
			}
		}
		return true;
	}

	public static boolean esIncompatibleConEquipo(Persona personaActual, Equipo equipoParcial,
			boolean[][] matrizIncompatibilidades, Map<Persona, Integer> cacheIndice) {
		int idxActual = cacheIndice.get(personaActual);
		for (Persona integrante : equipoParcial.obtenerIntegrantes()) {
			int idxIntegrante = cacheIndice.get(integrante);
			if (matrizIncompatibilidades[idxActual][idxIntegrante]) {
				return true;
			}
		}
		return false;
	}

	public static boolean debeNotificarProgreso(long tiempoActual, long ultimoTiempoNotificado, long umbralMs) {
	    return (tiempoActual - ultimoTiempoNotificado) > umbralMs;
	}

	public static long obtenerTiempoActual() {
		return System.currentTimeMillis();
	}
	
	public static boolean esPosibleAgregar(RolEnum rol, int cantidadRol, Map<RolEnum, Integer> requerimientos,boolean condicion) {
		int maximoRequerido = requerimientos.getOrDefault(rol, 0);
		return !(cantidadRol >= maximoRequerido) && condicion;
	}
}
