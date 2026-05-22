package equipoideal.model.listener;

public interface RequerimientosListener {
    /**
     * Este método se dispara cuando el usuario hace clic en "Agregar" en el JDialog.
     */
    void onRequerimientosAgregados(int cantLideres, int cantArquitectos, int cantProgramadores, int cantTesters);
}
