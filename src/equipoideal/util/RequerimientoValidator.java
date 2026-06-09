package equipoideal.util;

import java.util.List;
import java.util.Map;

import equipoideal.model.Persona;

public class RequerimientoValidator {
	
	public static void validarRequerimientos(Map<RolEnum, Integer> requerimientos, List<Persona> personas) {
		
		if (sumaRequerimientos(requerimientos) == 0) {
			throw new IllegalArgumentException("Debe haber al menos un rol seleccionado.");
		}
		
		if (sumaRequerimientos(requerimientos) > personas.size()) {
			throw new IllegalArgumentException("No hay suficientes personas para este equipo.");
		}
		
		for (RolEnum rol : requerimientos.keySet()) {
			int requeridos = requerimientos.get(rol);

			int disponibles = cantPersonasConRol(personas, rol);

			if (requeridos > disponibles) {
			
			throw new IllegalArgumentException("No hay suficientes personas para el rol "+ rol);
			}
		}
		
	}

	private static int sumaRequerimientos(Map<RolEnum, Integer> requerimientos) {
		int suma = 0;

		for (Integer cantidad : requerimientos.values()) {
			suma += cantidad;
		}

		return suma;
	}
	
	private static int cantPersonasConRol(List<Persona> personas, RolEnum rol) { 
		int contador = 0; 
		
		for (Persona p : personas) {
			
			if (p.getRol() == rol) { 
				contador++; 
			} 
		} 
		
		return contador; 
	}
}
