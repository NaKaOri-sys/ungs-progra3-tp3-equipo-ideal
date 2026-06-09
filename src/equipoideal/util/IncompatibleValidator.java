package equipoideal.util;

import java.util.List;

import equipoideal.model.Persona;

public class IncompatibleValidator {
	public static void validarIncompatible(List<Persona> personas, List<String> nombresFormateados) {
		if (personas == null || personas.size() == 0) 
			throw new IllegalArgumentException("No se pudo sincronizar el listado de personas");
		if (nombresFormateados == null || nombresFormateados.size() == 0) 
			throw new IllegalArgumentException("No se pudo sincronizar el listado de nombres");
	}
}
