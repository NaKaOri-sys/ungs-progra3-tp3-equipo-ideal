package equipoideal.model.listener;

import java.io.File;

public interface PersonaListener {
    /**
     * Este método se dispara cuando el usuario hace clic en "Agregar" en el JDialog.
     */
    void onPersonaAgregada();
    void onCargaDesdeJson(String ruta);
	void onFotoSeleccionada(File imagen);
	void onExportarJson(String rutaExportarArchivo);
	void onLimpiarCache();
	void onEliminarPersona();
	void onEdicionPersona();
	void onGuardarEdicionPersona();
}
