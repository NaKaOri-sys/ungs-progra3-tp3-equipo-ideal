package equipoideal.controller;

import java.util.ArrayList;

import equipoideal.model.Persona;
import equipoideal.model.PersonaDialogModel;
import equipoideal.model.dto.PersonaDto;
import equipoideal.model.event.IObserverPersonas;
import equipoideal.view.dialogs.RequerimientosDialog;

public class RequerimientoIntegrationController implements IObserverPersonas {
	
	private RequerimientosDialog vista;
	private PersonaDialogModel modelo;
	
	public RequerimientoIntegrationController(RequerimientosDialog vista, PersonaDialogModel modelo) {
		this.vista = vista;
		this.modelo = modelo;
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
	        dto.add(p.toDto());
	    }
		return dto;
	}
}