package equipoideal.model.dto;

import java.util.Map;
import java.util.Set;

public class IncompatibilidadDto {
	private Map<PersonaDto, Set<PersonaDto>> incompatibilidades;

	public IncompatibilidadDto(Map<PersonaDto, Set<PersonaDto>> incompatibilidades) {
		this.incompatibilidades = incompatibilidades;
	}

	public Map<PersonaDto, Set<PersonaDto>> getIncompatibilidades() {
		return incompatibilidades;
	}
}
