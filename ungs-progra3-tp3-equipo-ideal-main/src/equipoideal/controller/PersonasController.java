package equipoideal.controller;

import java.util.ArrayList;

import equipoideal.model.Persona;
import equipoideal.model.PersonaDialogModel;
import view.dialog.event.PersonasListener;
import view.dialog.event.PersonasObserver;
import view.dialogs.PersonasDialog;

public class PersonasController implements PersonasListener, PersonasObserver{
	
	private PersonasDialog vista;
    private PersonaDialogModel modelo;

    public PersonasController(PersonasDialog vista, PersonaDialogModel modelo) {
        this.vista = vista;
        this.modelo = modelo; 
        this.vista.setPersonasListener(this);
        this.modelo.addObserver(this);
    }
	@Override
	public void onPersonaAgregada(String nombre, String apellido, int puntos, String rol) {
		modelo.agregarPersona(nombre, apellido, puntos, rol);
	}
	
	@Override
	public void onListaPersonasModificada(ArrayList<Persona> nuevaLista) {
		modelo.guardarPersonaEnJSON();
	}
	
	

}
