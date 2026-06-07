package equipoideal.controller;

import java.util.ArrayList;
import java.util.List;
import equipoideal.model.Persona;
import equipoideal.model.IncompatibleModel; 
import equipoideal.model.listener.IncompatiblesListener;
import equipoideal.view.dialogs.IncompatibleDialog;
import equipoideal.view.dialogs.VentanaEmergente;

public class IncompatibleController implements IncompatiblesListener {

    private IncompatibleDialog vista;
    private IncompatibleModel incompatibleModel;   
    private List<Persona> listaPersonasTemporal; 

    // Ahora recibe la lista directa de personas .A
    public IncompatibleController(IncompatibleDialog vista, List<Persona> listaPersonas, IncompatibleModel incompatibleModel) {
        this.vista = vista;
        this.listaPersonasTemporal = listaPersonas;
        this.incompatibleModel = incompatibleModel;
        
        this.vista.setIncompatiblesListener(this);
        
        // La primera carga se hace con lo que haya al iniciar
        refrescarPantalla();
    }

    //  Este método lo va a llamar el NavigationController .C
    public void refrescarPantalla() {
        // Sincroniza el tamaño de la matriz con los empleados actuales
        this.incompatibleModel.actualizarTamañoMatriz(listaPersonasTemporal.size());
        
        // Vuelve a llenar los comboboxes de la pantalla
        List<String> nombresCompletos = new ArrayList<>();
        for (Persona empleado : listaPersonasTemporal) {
            nombresCompletos.add(empleado.getNombre() + " " + empleado.getApellido() + " (" + empleado.getRol() + ")");
        }
        vista.cargarPersonasEnSelectores(nombresCompletos);
    }
    
    @Override
    public void onIncompatibilidadRegistrada() {
        int indiceSeleccionadoA = vista.getIndexPersona1();
        int indiceSeleccionadoB = vista.getIndexPersona2();

        if (indiceSeleccionadoA == -1 || indiceSeleccionadoB == -1) 
        	return;

        if (indiceSeleccionadoA == indiceSeleccionadoB) {
        	vista.mostrarMensajeError("Una persona no puede ser incompatible con ella misma.");
            return;
        }

        incompatibleModel.registrarIncompatibilidad(indiceSeleccionadoA, indiceSeleccionadoB);

        String nombreEmpleadoA = obtenerNombrePorHistorial(indiceSeleccionadoA); 
        String nombreEmpleadoB = obtenerNombrePorHistorial(indiceSeleccionadoB);
        
        vista.agregarIncompatibilidadTabla(nombreEmpleadoA, nombreEmpleadoB);
        vista.limpiarInputs();
    }

    private String obtenerNombrePorHistorial(int indice) {
        if (indice >= 0 && indice < listaPersonasTemporal.size()) {
            Persona p = listaPersonasTemporal.get(indice);
            return p.getNombre() + " " + p.getApellido();
        }
        return "";
    }
    
    public boolean tienePersonasCargadas() {
        return this.listaPersonasTemporal != null && !this.listaPersonasTemporal.isEmpty();
    }
    
    public boolean tieneIncompatibilidadesRegistradas() {
        if (this.incompatibleModel == null) {
            return false;
        }
        return this.incompatibleModel.tieneIncompatibilidades();
    }
}