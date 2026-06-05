package equipoideal.controller;

import java.util.ArrayList;

import equipoideal.model.Persona;
import equipoideal.model.PersonaDialogModel;
import equipoideal.model.dto.PersonaDto;
import equipoideal.model.event.IObserverPersonas;
import equipoideal.view.dialogs.RequerimientosDialog;

public class RequerimientoIntegrationController implements IObserverPersonas {

	private RequerimientosDialog vista;
	private PersonaDialogModel modelo; //TODO porque inyectamos el modelo si no se usa?

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

	// TODO este transformar lo he visto en muchos lugares, se puede centralizar en
	// un helper o utilitario, o incluso dentro del modelo Persona como un método
	// estático que reciba una lista de Personas y devuelva una lista de PersonaDto.
	// Esto evitaría la duplicación de código y facilitaría el mantenimiento.
	private ArrayList<PersonaDto> transformar(ArrayList<Persona> lista) {
		ArrayList<PersonaDto> dto = new ArrayList<>();

		for (Persona p : lista) {
			dto.add(p.toDto());
		}
		return dto;
	}
}