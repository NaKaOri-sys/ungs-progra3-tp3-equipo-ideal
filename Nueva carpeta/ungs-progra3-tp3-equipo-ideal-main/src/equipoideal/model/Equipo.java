package equipoideal.model;

import java.util.ArrayList;
import java.util.List;

import equipoideal.model.dto.EquipoDto;
import equipoideal.model.dto.PersonaDto;

public class Equipo {
	private List<Persona> integrantes;

	public Equipo(List<Persona> integrantes) {
		this.integrantes = integrantes;
	}

	public List<Persona> obtenerIntegrantes() {
		return this.integrantes;
	}

	public int getCalificacionTotal() {
		int suma = 0;
		for (Persona persona : integrantes) {
			suma += persona.getCalificacion();
		}
		return suma;
	}

	public int getCantidadPorRol(String rol) {
		int suma = 0;
		for (Persona persona : integrantes) {
			String rolActual = persona.getRol();
			if (rol.equals(rolActual)) {
				suma += 1;
			}
		}
		return suma;
	}
	
	public EquipoDto toDto() {
		EquipoDto dto = new EquipoDto(new ArrayList<PersonaDto>());
		for (Persona persona : integrantes) {
			PersonaDto integrante = new PersonaDto(persona.getNombre(), persona.getApellido(), persona.getCalificacion(), persona.getRol());
			dto.getIntegrantes().add(integrante);
		}
		return dto;
	}
}
