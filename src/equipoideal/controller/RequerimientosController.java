package equipoideal.controller;

import java.util.ArrayList;

import equipoideal.model.Persona;
import equipoideal.model.PersonaDialogModel;
import equipoideal.model.dto.PersonaDto;
import equipoideal.model.listener.RequerimientosListener;
import equipoideal.view.dialogs.RequerimientosDialog;

public class RequerimientosController implements RequerimientosListener {
	private RequerimientosDialog vista;
	private PersonaDialogModel modelo;
	
	//TODO: Verificar si es valido usar PersonaDialogModel
	public RequerimientosController(RequerimientosDialog vista, PersonaDialogModel modelo) {
		this.vista = vista;
		this.modelo = modelo;
		this.vista.setRequerimientosListener(this);
	}
	
	
	@Override
	public void onRequerimientosAgregados() {
        int cantLideres = vista.getCantLider();
        int cantArquitectos = vista.getCantArquitecto();
        int cantProgramadores = vista.getCantProgramador();
        int cantTesters = vista.getCantTester();
        
//        modelo.crearRequerimientos(cantLideres, cantArquitectos, cantProgramadores, cantTesters);
        vista.limpiarInputs();
	}

}
