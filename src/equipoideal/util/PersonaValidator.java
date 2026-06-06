package equipoideal.util;

import equipoideal.model.dto.PersonaDto;

public class PersonaValidator {

	public static void validarPersona(PersonaDto dto) {

	    if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
	        throw new IllegalArgumentException("El nombre no puede estar vacío.");
	    }

	    if (dto.getApellido() == null || dto.getApellido().trim().isEmpty()) {
	        throw new IllegalArgumentException("El apellido no puede estar vacío.");
	    }

	    if (dto.getRol() == null) {
	        throw new IllegalArgumentException("El rol es inválido.");
	    }

	    if (dto.getNombre().length() < 2 || dto.getNombre().length() > 30) {
	        throw new IllegalArgumentException("El nombre debe tener entre 2 y 30 caracteres.");
	    }

	    if (dto.getApellido().length() < 2 || dto.getApellido().length() > 30) {
	        throw new IllegalArgumentException("El apellido debe tener entre 2 y 30 caracteres.");
	    }
	    
	    if (!contieneSoloLetras(dto.getNombre())) {
	        throw new IllegalArgumentException("El nombre solo puede tener letras.");
	    }
	    
	    if (!contieneSoloLetras(dto.getApellido())) {
	        throw new IllegalArgumentException("El apellido solo puede tener letras.");
	    }
	    
	    if (dto.getCalificacion() < 1 || dto.getCalificacion() > 5) {
	        throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5.");
	    }
	}
	
	private static boolean contieneSoloLetras(String texto) {
		for (int i = 0; i < texto.length(); i++) {
			
	        char caracter = texto.charAt(i); 
	        
	        if (!Character.isLetter(caracter) && !Character.isWhitespace(caracter)) {
	            return false;
	        }
	    }
	    return true;
	}
	
}