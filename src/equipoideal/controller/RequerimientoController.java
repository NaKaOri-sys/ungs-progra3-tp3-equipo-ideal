package equipoideal.controller;

import java.util.List;

import equipoideal.model.Persona;
import equipoideal.model.RequerimientoModel;
import equipoideal.model.dto.RequerimientoDto;
import equipoideal.model.listener.RequerimientoListener;
import equipoideal.view.dialogs.RequerimientoDialog;

public class RequerimientoController implements RequerimientoListener {
	private RequerimientoDialog vista;
	private RequerimientoModel modelo;
	private List<Persona> listaPersonasAuxiliar;
	
	public RequerimientoController(RequerimientoDialog vista, RequerimientoModel modelo) {
		this.vista = vista;
		this.modelo = modelo;
		this.vista.setRequerimientosListener(this);
		
	}
	
	@Override
	public void onRequerimientosAgregados() {
        RequerimientoDto dto = vista.getRequerimientos();
        
        try {
        modelo.crearRequerimientos(dto, listaPersonasAuxiliar);
        vista.ventanaMensaje("Se agregaron los requerimientos!");
        vista.limpiarInputs();
        
        } catch (IllegalArgumentException e) {
        	vista.ventanaMensaje(e.getMessage());
	    }
	}
	
	public void setListaPersonas(List<Persona> nuevaLista) {
		this.listaPersonasAuxiliar = nuevaLista;
	}
	
}
