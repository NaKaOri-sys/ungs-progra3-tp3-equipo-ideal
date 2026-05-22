package equipoideal.controller;

import java.util.ArrayList;

import equipoideal.model.Persona;
import equipoideal.model.PersonaDialogModel;
import equipoideal.model.event.PersonasObserver;
import equipoideal.model.listener.PersonasListener;
import equipoideal.view.dialogs.PersonasDialog;

//TODO si personaController tiene que escuchar dos interfaces, quiere decir que tenemos que dividir en 2 este controller,
//uno q escuche al listener y otro q solo escuche al observer
public class PersonasController implements PersonasListener, PersonasObserver {

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
