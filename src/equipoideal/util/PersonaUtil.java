package equipoideal.util;

import java.util.ArrayList;

import equipoideal.model.Persona;
import equipoideal.model.dto.PersonaDto;

public class PersonaUtil {
	
	public static ArrayList<PersonaDto> personasToDto(ArrayList<Persona> lista) {
		
		ArrayList<PersonaDto> dto = new ArrayList<>();

		for (Persona p : lista) {
			dto.add(p.toDto());
		}

		return dto;
	}
}
