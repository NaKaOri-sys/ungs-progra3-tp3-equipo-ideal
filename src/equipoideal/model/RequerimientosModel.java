package equipoideal.model;

import java.util.ArrayList;

import equipoideal.model.dto.RequerimientosDto;
import equipoideal.model.event.IObserverRequerimiento;
import equipoideal.util.Observable;
import equipoideal.util.RolEnum;

public class RequerimientosModel extends Observable<IObserverRequerimiento>{
	private ArrayList<Requerimiento> requerimientos;
	
	public RequerimientosModel()  {
		this.requerimientos = new  ArrayList<>();
	}

	public void crearRequerimientos(RequerimientosDto dto) {
		requerimientos.clear();

	    requerimientos.add(new Requerimiento(RolEnum.LIDER,dto.getLideres()));

	    requerimientos.add(new Requerimiento(RolEnum.ARQUITECTO, dto.getArquitectos()));

	    requerimientos.add(new Requerimiento(RolEnum.PROGRAMADOR, dto.getProgramadores()));

	    requerimientos.add(new Requerimiento(RolEnum.TESTER, dto.getTesters()));
	    
	    //TODO: Verificar si se pasa por Observer o por Get
	    notifyObservers(observer -> observer.onRequerimientosCreados(requerimientos));
	    
	}

	public  ArrayList<Requerimiento> getRequerimientos(){
		return requerimientos;
	}
	
}
