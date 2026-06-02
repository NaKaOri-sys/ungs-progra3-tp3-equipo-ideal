package equipoideal.util;

import java.util.List;
import java.util.Map;

import equipoideal.model.Equipo;
import equipoideal.model.Persona;
import equipoideal.model.Requerimiento;
import equipoideal.model.dto.ProgresoEventoDto;
import equipoideal.model.event.IObserverCalculador;

public class EquipoCalculadorUtil {

	public static boolean cumpleConLosRequerimientos(Equipo equipo, List<Requerimiento> requerimientos) {
		for (int i = 0; i < requerimientos.size(); i++) {
			int requerido = requerimientos.get(i).getCantidad();
			if (equipo.getCantidadPorRol(requerimientos.get(i).getRol()) != requerido) {
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

	public static boolean debeNotificarProgreso(long ultimoTiempoNotificado, long umbralMs) {
		long tiempoActual = System.currentTimeMillis();
		long tiempoTranscurrido = tiempoActual - ultimoTiempoNotificado;
		return tiempoTranscurrido > umbralMs;
	}

	public static long obtenerTiempoActual() {
		return System.currentTimeMillis();
	}
	
	public static boolean esPosibleAgregar(RolEnum rol, int cantidadRol, List<Requerimiento> requerimientos,boolean condicion) {
		int maximoRequerido = 0;
		for (int i = 0; i < requerimientos.size(); i++) {
			if (requerimientos.get(i).getRol().equals(rol)) {
				maximoRequerido = requerimientos.get(i).getCantidad();
			}
		}
		return !(cantidadRol >= maximoRequerido) && condicion;
	}
}
