package equipoideal.controller;

import java.util.ArrayList;

import equipoideal.model.PersonaModel;
import equipoideal.model.RequerimientoModel;
import equipoideal.model.dto.PersonaDto;
import equipoideal.model.dto.RequerimientoDto;
import equipoideal.model.listener.RequerimientoListener;
import equipoideal.view.dialogs.RequerimientoDialog;

public class RequerimientoController implements RequerimientoListener {
	private RequerimientoDialog vista;
	private RequerimientoModel modelo;
	
	public RequerimientoController(RequerimientoDialog vista, RequerimientoModel modelo, PersonaModel modeloPersonas) {
		this.vista = vista;
		this.modelo = modelo;
		this.vista.setRequerimientosListener(this);
		
		modeloPersonas.addObserver(nuevaLista -> {
			if (nuevaLista != null && !nuevaLista.isEmpty()) {
				ArrayList<PersonaDto> dto = nuevaLista.get(0).transformarEnDto(nuevaLista);
				this.vista.actualizarTablaPersonas(dto);
			}
		});
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
