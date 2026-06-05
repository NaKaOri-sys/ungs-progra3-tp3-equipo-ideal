package equipoideal.model;

import java.util.ArrayList;

import equipoideal.model.dto.RequerimientosDto;
import equipoideal.model.event.IObserverRequerimiento;
import equipoideal.util.Observable;
import equipoideal.util.RequerimientoValidator;
import equipoideal.util.RolEnum;

public class RequerimientosModel extends Observable<IObserverRequerimiento> {
	private ArrayList<Requerimiento> requerimientos;
	private PersonaDialogModel personaModel;

	public RequerimientosModel(PersonaDialogModel personaModel) {
		this.requerimientos = new ArrayList<>();
		this.personaModel = personaModel;
	}

	// TODO se puede simplificar esto con un Map para evitar repetir el mismo código
	// para cada rol, y que el validator se encargue de validar cada rol en lugar de
	// tener una validación específica para cada uno. Esto reduciría la cantidad de
	// código y centralizaría la lógica de validación en un solo lugar.
	public void crearRequerimientos(RequerimientosDto dto) {
		requerimientos.clear();

		RequerimientoValidator.validarRequerimientos(dto, personaModel.getListaPersonas());

		requerimientos.add(new Requerimiento(RolEnum.LIDER, dto.getLideres()));

		requerimientos.add(new Requerimiento(RolEnum.ARQUITECTO, dto.getArquitectos()));

		requerimientos.add(new Requerimiento(RolEnum.PROGRAMADOR, dto.getProgramadores()));

		requerimientos.add(new Requerimiento(RolEnum.TESTER, dto.getTesters()));

		// TODO: Verificar si se pasa por Observer o por Get
		notifyObservers(observer -> observer.onRequerimientosCreados(requerimientos));

	}

	public ArrayList<Requerimiento> getRequerimientos() {
		return requerimientos;
	}

}
