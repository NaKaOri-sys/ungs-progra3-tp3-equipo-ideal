package equipoideal.controller;

import java.util.ArrayList;

import equipoideal.model.Persona;
import equipoideal.model.PersonaDialogModel;
import equipoideal.model.dto.PersonaDto;
import equipoideal.model.event.PersonasObserver;
import equipoideal.view.dialogs.RequerimientosDialog;

public class RequerimientoIntegrationController implements PersonasObserver {
	
	private RequerimientosDialog vista;
	
	public RequerimientoIntegrationController(RequerimientosDialog vista, PersonaDialogModel modelo) {
		this.vista = vista;
		modelo.addObserver(this);
	}
	
	@Override
	public void onListaPersonasModificada(ArrayList<Persona> nuevaLista) {
		ArrayList<PersonaDto> dto = transformar(nuevaLista);
		vista.actualizarTablaPersonas(dto);
	}
	
	private ArrayList<PersonaDto> transformar(ArrayList<Persona> lista) {
		ArrayList<PersonaDto> dto = new ArrayList<>();
		
		for (Persona p : lista) {
			dto.add(new PersonaDto(
            p.getNombre(),
            p.getApellido(),
            p.getCalificacion(),
            p.getRol()
            ));
		}
		return dto;
	}
}