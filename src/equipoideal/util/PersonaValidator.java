package equipoideal.util;

public class PersonaValidator {

	public static void validarPersona(String nombre, String apellido, int puntos, RolEnum rol) {

	    if (nombre == null || nombre.trim().isEmpty()) {
	        throw new IllegalArgumentException("El nombre no puede estar vacío.");
	    }

	    if (apellido == null || apellido.trim().isEmpty()) {
	        throw new IllegalArgumentException("El apellido no puede estar vacío.");
	    }

	    if (rol == null) {
	        throw new IllegalArgumentException("El rol es inválido.");
	    }

	    if (nombre.length() < 2 || nombre.length() > 30) {
	        throw new IllegalArgumentException("El nombre debe tener entre 2 y 30 caracteres.");
	    }

	    if (apellido.length() < 2 || apellido.length() > 30) {
	        throw new IllegalArgumentException("El apellido debe tener entre 2 y 30 caracteres.");
	    }
	    
	    if (!contieneSoloLetras(nombre)) {
	        throw new IllegalArgumentException("El nombre solo puede tener letras.");
	    }
	    
	    if (!contieneSoloLetras(apellido)) {
	        throw new IllegalArgumentException("El apellido solo puede tener letras.");
	    }
	    
	    if (puntos < 1 || puntos > 5) {
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