package equipoideal.controller;

import java.util.ArrayList;

import equipoideal.model.Persona;
import equipoideal.model.PersonaDialogModel;
import equipoideal.model.dto.PersonaDto;
import equipoideal.model.event.IObserverPersonas;
import equipoideal.view.dialogs.PersonasDialog;


public class PersonaIntegrationController implements IObserverPersonas {
	private PersonasDialog vista;
    private PersonaDialogModel modelo;
    
    public PersonaIntegrationController(PersonasDialog vista, PersonaDialogModel modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.modelo.addObserver(this);
    }
	
    @Override
    public void onListaPersonasModificada(ArrayList<Persona> nuevaLista) {
    	
    	modelo.guardarPersonaEnJSON();
    	
        ArrayList<PersonaDto> personasDTO = transformarDTO(nuevaLista);

        vista.actualizarTablaPersonas(personasDTO);
    }
    
    private ArrayList<PersonaDto> transformarDTO(ArrayList<Persona> lista) {

        ArrayList<PersonaDto> dto = new ArrayList<>();

        for (Persona p : lista) {
            dto.add(p.toDto());
        }

        return dto;
    }
}
