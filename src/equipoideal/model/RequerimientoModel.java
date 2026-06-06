package equipoideal.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import equipoideal.model.dto.RequerimientoDto;
import equipoideal.model.event.IObserverRequerimiento;
import equipoideal.util.Observable;
import equipoideal.util.RequerimientoValidator;
import equipoideal.util.RolEnum;

public class RequerimientoModel extends Observable<IObserverRequerimiento> {
	private ArrayList<Requerimiento> requerimientos;
	private PersonaModel personaModel;

	public RequerimientoModel(PersonaModel personaModel) {
		this.requerimientos = new ArrayList<>();
		this.personaModel = personaModel;
	}

	// TODO Verificar si esto esta bien
	public void crearRequerimientos(RequerimientoDto dto) {
		requerimientos.clear();
		
		Map<RolEnum, Integer> mapa = new LinkedHashMap<>();

		mapa.put(RolEnum.LIDER, dto.getLideres());

		mapa.put(RolEnum.ARQUITECTO, dto.getArquitectos());

		mapa.put(RolEnum.PROGRAMADOR, dto.getProgramadores());

		mapa.put(RolEnum.TESTER, dto.getTesters());

		RequerimientoValidator.validarRequerimientos(mapa, personaModel.getListaPersonas());

		for (RolEnum rol : mapa.keySet()) {
			requerimientos.add(new Requerimiento(rol,mapa.get(rol)));
		}
		
		notifyObservers(observer -> observer.onRequerimientosCreados(requerimientos));
	}

	public ArrayList<Requerimiento> getRequerimientos() {
		return requerimientos;
	}

}
