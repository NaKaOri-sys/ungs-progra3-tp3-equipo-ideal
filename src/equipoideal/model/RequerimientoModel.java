package equipoideal.model;

import java.util.LinkedHashMap;
import java.util.Map;

import equipoideal.model.dto.RequerimientoDto;
import equipoideal.model.event.IObserverRequerimiento;
import equipoideal.util.Observable;
import equipoideal.util.RequerimientoValidator;
import equipoideal.util.RolEnum;

// TODO: Evitar mantener PersonaModel como variable de instancia permanente únicamente para validar los requerimientos. Pasar la lista de personas como parámetro directo al método crearRequerimientos para reducir el acoplamiento.
public class RequerimientoModel extends Observable<IObserverRequerimiento> {
	private Map<RolEnum, Integer> requerimientos;
	private PersonaModel personaModel;

	public RequerimientoModel(PersonaModel personaModel) {
		this.requerimientos = new LinkedHashMap<>();
		this.personaModel = personaModel;
	}

	public void crearRequerimientos(RequerimientoDto dto) {
		requerimientos.clear();

		requerimientos.put(RolEnum.LIDER, dto.getLideres());

		requerimientos.put(RolEnum.ARQUITECTO, dto.getArquitectos());

		requerimientos.put(RolEnum.PROGRAMADOR, dto.getProgramadores());

		requerimientos.put(RolEnum.TESTER, dto.getTesters());

		RequerimientoValidator.validarRequerimientos(requerimientos, personaModel.getListaPersonas());

		

		notifyObservers(observer -> observer.onRequerimientosCreados(requerimientos));
	}

	public Map<RolEnum, Integer> getRequerimientos() {
		return requerimientos;
	}

}