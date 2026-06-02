package equipoideal.util;

import java.util.ArrayList;

import equipoideal.model.Persona;
import equipoideal.model.dto.RequerimientosDto;

public class RequerimientoValidator {
	
	public static void validarRequerimientos(RequerimientosDto dto, ArrayList<Persona> personas) {
		
		if (sumaRequerimientos(dto) == 0) {
			throw new IllegalArgumentException("Debe haber al menos un rol seleccionado.");
		}
		
		if (sumaRequerimientos(dto) > personas.size()) {
			throw new IllegalArgumentException("No hay suficientes personas para este equipo.");
		}
		
		if (dto.getLideres() > cantPersonasConRol(personas, RolEnum.LIDER)) {
			throw new IllegalArgumentException("No hay suficientes Lideres para este equipo.");
		}
		
		if (dto.getArquitectos() > cantPersonasConRol(personas, RolEnum.ARQUITECTO)) {
			throw new IllegalArgumentException("No hay suficientes Arquitectos para este equipo.");
		}
		
		if (dto.getTesters() > cantPersonasConRol(personas, RolEnum.TESTER)) {
			throw new IllegalArgumentException("No hay suficientes Testers para este equipo.");
		}
		
		if (dto.getProgramadores() > cantPersonasConRol(personas, RolEnum.PROGRAMADOR)) {
			throw new IllegalArgumentException("No hay suficientes Programadores para este equipo.");
		}
		
	}

	private static int sumaRequerimientos(RequerimientosDto dto) {
		int suma = dto.getLideres() + dto.getArquitectos() + dto.getTesters() + dto.getProgramadores();
		return suma;
	}
	private static int cantPersonasConRol(ArrayList<Persona> personas, RolEnum rol) { 
		int contador = 0; 
		
		for (Persona p : personas) {
			
			if (p.getRol() == rol) { 
				contador++; } 
			
		} return contador; 
	}
}
