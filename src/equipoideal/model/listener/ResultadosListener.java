package equipoideal.model.listener;

public interface ResultadosListener {
    void onVerDetalle(int indexEquipo); //indice para saber que equipo se eligio
    void onElegirSeleccionada(int index);
    void onVolverMenu();
    void onCerrarDetalle();
    void onVerEmpleado(int indexEmpleado);
}	