package equipoideal.controller;

import equipoideal.model.RequerimientoModel;
import equipoideal.model.dto.RequerimientoDto;
import equipoideal.model.listener.RequerimientoListener;
import equipoideal.view.dialogs.RequerimientoDialog;

public class RequerimientoController implements RequerimientoListener {
	private RequerimientoDialog vista;
	private RequerimientoModel modelo;
	
	public RequerimientoController(RequerimientoDialog vista, RequerimientoModel modelo) {
		this.vista = vista;
		this.modelo = modelo;
		this.vista.setRequerimientosListener(this);
		
	}
	
	@Override
	public void onRequerimientosAgregados() {
        RequerimientoDto dto = vista.getRequerimientos();
        
        try {
        modelo.crearRequerimientos(dto);
        vista.setRequerimientosActuales(dto);
        vista.ventanaMensaje("Se agregaron los requerimientos!");
        vista.limpiarInputs();
        
        } catch (IllegalArgumentException e) {
        	vista.ventanaMensaje(e.getMessage());
	    }
	}
	
}
