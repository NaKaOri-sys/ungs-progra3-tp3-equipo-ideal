package equipoideal.model.listener;

import java.awt.event.ActionEvent;

public interface IMenuListener {
	 /**
     * Este método se dispara cuando el usuario hace clic en "Cargar Personas" en el menú.
     */
		void onCargarPersonas();
		
	/**
	 * Este método se dispara cuando el usuario hace clic en "Requerimientos" en el menú.
	 */
		void onRequerimientos();
	
	/**
	 * Este método se dispara cuando el usuario hace clic en "Incompatibilidad" en el menú.
	 */
		void onIncompatibilidad();
	
	/**
	 * Este método se dispara cuando el usuario hace clic en "Búsqueda" en el menú.
	 */
		void onBusqueda();

}
