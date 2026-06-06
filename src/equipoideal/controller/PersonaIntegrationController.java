package equipoideal.controller;


import java.util.ArrayList;

import equipoideal.model.Persona;
import equipoideal.model.PersonaModel;
import equipoideal.model.dto.PersonaDto;
import equipoideal.model.event.IObserverPersona;
import equipoideal.view.dialogs.PersonaDialog;


public class PersonaIntegrationController implements IObserverPersona {
	private PersonaDialog vista;

    public PersonaIntegrationController(PersonaDialog vista, PersonaModel modelo) {
        this.vista = vista;
        modelo.addObserver(this);
    }

    @Override
	public void onListaPersonasModificada(ArrayList<Persona> nuevaLista) {
		ArrayList<PersonaDto> dto = nuevaLista.get(0).transformarEnDto(nuevaLista);
		vista.actualizarTablaPersonas(dto);
	}

}