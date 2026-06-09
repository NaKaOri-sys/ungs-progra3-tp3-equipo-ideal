package equipoideal.util;

import java.util.ArrayList;
import java.util.List;

import equipoideal.model.Persona;
import equipoideal.model.dto.PersonaDto;

public class PersonaUtil {
	
	public static List<PersonaDto> personasToDto(List<Persona> lista) {
		
		ArrayList<PersonaDto> dto = new ArrayList<>();

		for (Persona p : lista) {
			dto.add(p.toDto());
		}

		return dto;
	}
}
