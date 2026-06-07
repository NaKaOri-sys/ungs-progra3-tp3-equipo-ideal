package equipoideal.controller;

import equipoideal.model.IncompatibleModel;
import equipoideal.model.PersonaModel;
import equipoideal.model.RequerimientoModel;
import equipoideal.model.listener.IMenuListener;
import equipoideal.view.MenuView;
import equipoideal.view.dialogs.IncompatibleDialog;
import equipoideal.view.dialogs.PersonaDialog;
import equipoideal.view.dialogs.RequerimientoDialog;
import equipoideal.view.dialogs.VentanaEmergente;



public class MenuController implements IMenuListener {
	private NavigationController navigationController;
	private MenuView menuView;
	
	private PersonaDialog personaDialog;
	private RequerimientoDialog requerimientosDialog;
	private IncompatibleDialog incompatibleDialog;
	
	private PersonaController personaController;
	private RequerimientoController requerimientoController;
	private IncompatibleController incompatibleController;
	
	private PersonaModel personaModel;
	private RequerimientoModel requerimientoModel;
	private IncompatibleModel incompatibleModel;
	

	public MenuController(NavigationController navigationController, MenuView menuView, PersonaDialog personaDialog,
			RequerimientoDialog requerimientosDialog, IncompatibleDialog incompatibleDialog,
			PersonaController personaController, RequerimientoController requerimientoController, IncompatibleController incompatibleController,
			PersonaModel personaModel, RequerimientoModel requerimientoModel, IncompatibleModel incompatibleModel) {
		this.navigationController = navigationController;
		this.menuView = menuView;
		this.personaDialog = personaDialog;
		this.requerimientosDialog = requerimientosDialog;
		this.incompatibleDialog = incompatibleDialog;
		
		this.personaModel = personaModel;
		this.requerimientoModel = requerimientoModel;
		this.incompatibleModel = incompatibleModel;
		
		this.personaController = personaController;
		this.requerimientoController = requerimientoController;
		this.incompatibleController = incompatibleController;

		this.menuView.obtenerObserver().addObserver(this);
	}
	
	@Override
	public void onCargarPersonas() {
		personaDialog.setVisible(true);
	}

	@Override
	public void onRequerimientos() {
		requerimientosDialog.setVisible(true);
	}

@Override
    public void onIncompatibilidad() {
        
        if (this.personaModel.getListaPersonas().isEmpty()) {
            VentanaEmergente aviso = new VentanaEmergente(null, "Debe cargar personas en el sistema antes de registrar incompatibilidades.");
            aviso.setVisible(true);
            return;
        }
        incompatibleDialog.setVisible(true);
    }

	@Override
	public void onBusqueda() {
		
		if(this.personaModel.getListaPersonas().isEmpty())  {
			VentanaEmergente aviso = new VentanaEmergente(null, "Debe cargar personas en el sistema antes de buscar un equipo ideal.");
			aviso.setVisible(true);
			return;
		}
		if(this.requerimientoModel.getRequerimientos().isEmpty())  {
			VentanaEmergente aviso = new VentanaEmergente(null, "Debe cargar requerimientos en el sistema antes de buscar un equipo ideal.");
			aviso.setVisible(true);
			return;
		}
		this.navigationController.eventSearch();
	}

	public void dispose() {
		this.menuView.obtenerObserver().removeObserver(this);
	}
}
