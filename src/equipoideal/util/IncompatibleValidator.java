package equipoideal.util;

import java.util.List;

import equipoideal.model.Persona;

public class IncompatibleValidator {
	public static void validarIncompatible(List<Persona> personas) {
		if (personas == null || personas.size() == 0) 
			throw new IllegalArgumentException("No se pudo sincronizar el listado de personas");
	}
}
