package equipoideal.controller;

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
	private IncompatibleController incompatibleController;

	public MenuController(NavigationController navigationController, MenuView menuView, PersonaDialog personaDialog,
			RequerimientoDialog requerimientosDialog, IncompatibleDialog incompatibleDialog) {
		this.navigationController = navigationController;
		this.menuView = menuView;
		this.personaDialog = personaDialog;
		this.requerimientosDialog = requerimientosDialog;
		this.incompatibleDialog = incompatibleDialog;

		this.menuView.obtenerObserver().addObserver(this);
	}
	
	
	public void setIncompatibleController(IncompatibleController incompatibleController) {
        this.incompatibleController = incompatibleController;
    }

	// TODO agregar los controllers de los dialogs a este controllers ya que es
	// donde más sentido tiene, los modelos deben ser inyectados desde el main y
	// pasados a los controllers de los dialogs, para que estos puedan actualizarse
	// entre sí
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
        
        if (this.incompatibleController != null) {
            this.incompatibleController.refrescarPantalla();
        }
        
       //TODO: sacar esto de aca
        if (incompatibleDialog.getIndexPersona1() == -1) {
            VentanaEmergente aviso = new VentanaEmergente(null, "Debe cargar personas en el sistema antes de registrar incompatibilidades.");
            aviso.setVisible(true);
//            return; // Bloquea la apertura
//        	menuView.mostrarAdvertencia("Debe cargar personas en el sistema antes de registrar incompatibilidades.");
//        	mainView.mostrarAlertaGlobal("Debe cargar personas en el sistema antes de registrar incompatibilidades.");
            return;
        }
        
        
        incompatibleDialog.setVisible(true);
    }

	// TODO si no hay personas, requerimientos o incompatibilidades cargados, debe
	// estar bloqueada la opción de búsqueda, y mostrar un mensaje indicando que se
	// deben cargar los datos primero
	@Override
	public void onBusqueda() {
		this.navigationController.eventSearch();
	}

	public void dispose() {
		this.menuView.obtenerObserver().removeObserver(this);
	}
}
