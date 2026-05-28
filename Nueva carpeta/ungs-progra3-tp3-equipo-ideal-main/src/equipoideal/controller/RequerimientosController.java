package equipoideal.controller;

import equipoideal.model.listener.RequerimientosListener;
import equipoideal.view.dialogs.RequerimientosDialog;

public class RequerimientosController implements RequerimientosListener {
	private RequerimientosDialog vista;
	
	public RequerimientosController(RequerimientosDialog vista) {
		this.vista = vista;
		this.vista.setRequerimientosListener(this);
	}
	
	@Override
	public void onRequerimientosAgregados() {
        int cantLideres = vista.getCantLider();
        int cantArquitectos = vista.getCantArquitecto();
        int cantProgramadores = vista.getCantProgramador();
        int cantTesters = vista.getCantTester();
	}

}
