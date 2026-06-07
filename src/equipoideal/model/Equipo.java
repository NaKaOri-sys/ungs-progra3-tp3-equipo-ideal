package equipoideal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import equipoideal.model.dto.EquipoDto;
import equipoideal.model.dto.PersonaDto;
import equipoideal.util.RolEnum;

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

	public int getCantidadPorRol(RolEnum rolEnum) {
		if (rolEnum == null)
			throw new IllegalArgumentException("El rol no puede estar vacio.");
		int suma = 0;
		for (Persona persona : integrantes) {
			if (rolEnum.equals(persona.getRol())) {
				suma += 1;
			}
		}
		return suma;
	}

	public EquipoDto toDto() {
		EquipoDto dto = new EquipoDto(new ArrayList<PersonaDto>());
		for (Persona persona : integrantes) {
			PersonaDto integrante = persona.toDto();
			dto.getIntegrantes().add(integrante);
		}
		return dto;
	}

	@Override
	public int hashCode() {
		return Objects.hash(integrantes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Equipo)) {
			return false;
		}
		Equipo other = (Equipo) obj;
		return Objects.equals(integrantes, other.integrantes);
	}
}
