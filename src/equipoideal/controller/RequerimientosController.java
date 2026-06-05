package equipoideal.controller;

import equipoideal.model.RequerimientosModel;
import equipoideal.model.dto.RequerimientosDto;
import equipoideal.model.listener.RequerimientosListener;
import equipoideal.view.dialogs.RequerimientosDialog;

public class RequerimientosController implements RequerimientosListener {
	private RequerimientosDialog vista;
	private RequerimientosModel modelo;
	
	//TODO faltaria un eliminar o editar los requerimientos ya que si uno se equivoca no hay forma de corregirlo.
	public RequerimientosController(RequerimientosDialog vista, RequerimientosModel modelo) {
		this.vista = vista;
		this.modelo = modelo;
		this.vista.setRequerimientosListener(this);
	}
	
	@Override
	public void onRequerimientosAgregados() {
        RequerimientosDto dto = vista.getRequerimientos();
        
        try {
        modelo.crearRequerimientos(dto); //TODO mostrar feedback al usuario ya que no se muestra nada y es confuso xd
        } catch (IllegalArgumentException e) {
        	//TODO mostrar error al usuario
	        System.out.println(e.getMessage());
	    }
        vista.limpiarInputs();
	}

}
