package equipoideal.controller;

import java.util.ArrayList;

import equipoideal.model.Persona;
import equipoideal.model.PersonaDialogModel;
import equipoideal.model.event.PersonasObserver;
import equipoideal.view.dialogs.PersonasDialog;


public class PersonaIntegrationController implements PersonasObserver {
	private PersonasDialog vista;
    private PersonaDialogModel modelo;
    
    public PersonaIntegrationController(PersonasDialog vista, PersonaDialogModel modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.modelo.addObserver(this);
    }
	
	@Override
    public void onListaPersonasModificada(ArrayList<Persona> nuevaLista) {
        
        Object[][] datosTabla = transformarMatriz(nuevaLista);
        vista.actualizarTablaPersonas(datosTabla);
    }
    
	//TODO ver de crear un DTO para no usar Object -> es mala practica porque es muy generico Objects
    private Object[][] transformarMatriz(ArrayList<Persona> lista) {
        // Lógica para pasar de List<Persona> a Object[][] para la JTable
        Object[][] matriz = new Object[lista.size()][4];
        for (int i = 0; i < lista.size(); i++) {
            Persona p = lista.get(i);
            matriz[i][0] = p.getNombre();
            matriz[i][1] = p.getApellido();
            matriz[i][2] = p.getRol();
            matriz[i][3] = p.getPuntos();
        }
        return matriz;
    }

	
}
