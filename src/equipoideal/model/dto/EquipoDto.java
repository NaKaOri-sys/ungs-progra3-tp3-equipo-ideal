package equipoideal.model.dto;

import java.util.List;

public class EquipoDto {
	private List<PersonaDto> integrantes;
	
	public EquipoDto(List<PersonaDto> integrantes) {
		this.integrantes = integrantes;
	}
	
	public List<PersonaDto> getIntegrantes() {
		return this.integrantes;
	}

}
