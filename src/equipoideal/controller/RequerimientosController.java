package equipoideal.controller;

import equipoideal.model.RequerimientosModel;
import equipoideal.model.dto.RequerimientosDto;
import equipoideal.model.listener.RequerimientosListener;
import equipoideal.view.dialogs.RequerimientosDialog;

public class RequerimientosController implements RequerimientosListener {
	private RequerimientosDialog vista;
	private RequerimientosModel modelo;
	
	
	public RequerimientosController(RequerimientosDialog vista, RequerimientosModel modelo) {
		this.vista = vista;
		this.modelo = modelo;
		this.vista.setRequerimientosListener(this);
	}
	
	
	@Override
	public void onRequerimientosAgregados() {
        RequerimientosDto dto = vista.getRequerimientos();
        
        try {
        modelo.crearRequerimientos(dto);
        } catch (IllegalArgumentException e) {

	        System.out.println(e.getMessage());
	    }
        vista.limpiarInputs();
	}

}
