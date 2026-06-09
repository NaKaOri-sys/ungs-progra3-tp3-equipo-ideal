package equipoideal.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import equipoideal.model.dto.RequerimientoDto;
import equipoideal.model.event.IObserverRequerimiento;
import equipoideal.util.Observable;
import equipoideal.util.RequerimientoValidator;
import equipoideal.util.RolEnum;

public class RequerimientoModel extends Observable<IObserverRequerimiento> {
	private Map<RolEnum, Integer> requerimientos;

	public RequerimientoModel() {
		this.requerimientos = new LinkedHashMap<>();
	}

	public void crearRequerimientos(RequerimientoDto dto, List<Persona> personas) {
		Map<RolEnum, Integer> temp = new LinkedHashMap<>();

		requerimientos.put(RolEnum.LIDER, dto.getLideres());

		requerimientos.put(RolEnum.ARQUITECTO, dto.getArquitectos());

		requerimientos.put(RolEnum.PROGRAMADOR, dto.getProgramadores());

		requerimientos.put(RolEnum.TESTER, dto.getTesters());

		RequerimientoValidator.validarRequerimientos(temp, personas);
		this.requerimientos = temp;

		notifyObservers(observer -> observer.onRequerimientosCreados(dto));
	}

	public Map<RolEnum, Integer> getRequerimientos() {
		return requerimientos;
	}

	public boolean hayRequerimientosCargados() {
		return requerimientos.size() > 0;
	}

}