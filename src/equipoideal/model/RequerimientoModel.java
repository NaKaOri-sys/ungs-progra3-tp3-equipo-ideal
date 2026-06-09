package equipoideal.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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

	public void crearRequerimientos(RequerimientoDto dto, ArrayList<Persona> personas) {
		requerimientos.clear();

		requerimientos.put(RolEnum.LIDER, dto.getLideres());

		requerimientos.put(RolEnum.ARQUITECTO, dto.getArquitectos());

		requerimientos.put(RolEnum.PROGRAMADOR, dto.getProgramadores());

		requerimientos.put(RolEnum.TESTER, dto.getTesters());

		RequerimientoValidator.validarRequerimientos(requerimientos, personas);

		

		notifyObservers(observer -> observer.onRequerimientosCreados(dto));
	}

	public Map<RolEnum, Integer> getRequerimientos() {
		return requerimientos;
	}
	
	public boolean hayRequerimientosCargados() {
		return requerimientos.size() > 0;
	}

}