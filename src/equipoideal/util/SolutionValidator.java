package equipoideal.util;

import java.util.List;
import java.util.Map;

import equipoideal.model.Persona;

public class SolutionValidator {
	
	public static void solutionValidator(List<Persona> personas, Map<RolEnum, Integer> requerimientosRoles,
			boolean[][] matrizIncompatibilidades) {
		if (personas == null || personas.isEmpty())
			throw new IllegalArgumentException("La lista de personas no puede estar vacia.");
		if (requerimientosRoles == null || requerimientosRoles.isEmpty())
			throw new IllegalArgumentException("La lista de requerimientos por rol, no puede estar vacia.");
		if (matrizIncompatibilidades == null || matrizIncompatibilidades.length == 0)
			throw new IllegalArgumentException("La matriz de incompatibilidades, no puede estar vacia.");
		if (matrizIncompatibilidades.length != personas.size())
			throw new IllegalArgumentException("La matriz de incompatibilidades, tiene que tener exactamente la misma longitud que la lista de personas.");
	}
}
