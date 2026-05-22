package equipoideal.model.listener;

public interface PersonasListener {
    /**
     * Este método se dispara cuando el usuario hace clic en "Agregar" en el JDialog.
     */
    void onPersonaAgregada(String nombre, String apellido, int puntos, String rol);
}
