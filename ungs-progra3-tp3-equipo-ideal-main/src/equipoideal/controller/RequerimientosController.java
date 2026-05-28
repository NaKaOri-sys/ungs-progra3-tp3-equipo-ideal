package equipoideal.controller;

import view.dialog.event.RequerimientosListener;

public class RequerimientosController implements RequerimientosListener {

	@Override
	public void onRequerimientosAgregados(int cantLideres, int cantArquitectos, int cantProgramadores,
			int cantTesters) {
        System.out.println("Líderes: " + cantLideres + " | Arqs: " + cantArquitectos 
        		+ "| Prog: " + cantProgramadores + "| Testers: " + cantTesters);       //Prueba
	}

}
