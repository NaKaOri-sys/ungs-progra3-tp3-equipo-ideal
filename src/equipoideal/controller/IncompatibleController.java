package equipoideal.controller;

import java.util.ArrayList;
import java.util.List;
import equipoideal.model.Persona;
import equipoideal.model.PersonaModel;
import equipoideal.model.IncompatibleModel; 
import equipoideal.model.listener.IncompatiblesListener;
import equipoideal.view.dialogs.IncompatibleDialog;
import equipoideal.view.dialogs.VentanaEmergente;

public class IncompatibleController implements IncompatiblesListener {

    private IncompatibleDialog vista;
    private PersonaModel personaDialogModel; 
    private IncompatibleModel incompatibleModel;   
    private List<Persona> listaPersonasTemporal; 

    public IncompatibleController(IncompatibleDialog vista, PersonaModel personaDialogModel, IncompatibleModel incompatibleModel) {
        this.vista = vista;
        this.personaDialogModel = personaDialogModel;
        this.incompatibleModel = incompatibleModel;        
        this.vista.setIncompatiblesListener(this);
        this.listaPersonasTemporal = new ArrayList<>();
        this.incompatibleModel.actualizarTamañoMatriz(personaDialogModel.getListaPersonas().size());
        
        actualizarSelectores();
    }

    public void actualizarSelectores() {
        listaPersonasTemporal = personaDialogModel.getListaPersonas();
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

        if (indiceSeleccionadoA == -1 || indiceSeleccionadoB == -1) return;

        if (indiceSeleccionadoA == indiceSeleccionadoB) {
            VentanaEmergente error = new VentanaEmergente(vista, "Una persona no puede ser incompatible con ella misma.");
            error.setVisible(true);
            return;
        }

        incompatibleModel.registrarIncompatibilidad(indiceSeleccionadoA, indiceSeleccionadoB);


        String nombreEmpleadoA = personaDialogModel.obtenerNombrePorIndice(indiceSeleccionadoA); 
        String nombreEmpleadoB = personaDialogModel.obtenerNombrePorIndice(indiceSeleccionadoB);
        
        vista.agregarIncompatibilidadTabla(nombreEmpleadoA, nombreEmpleadoB);
        vista.limpiarInputs();
    }
}