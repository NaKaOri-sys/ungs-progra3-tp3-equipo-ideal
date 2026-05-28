package equipoideal.model;

import equipoideal.model.dto.ProgresoEventoDto;
import equipoideal.model.event.IObserverBacktracking;
import equipoideal.util.Observable;

public class CalculadorBacktracking extends Observable<IObserverBacktracking> {
	// Configuración del Top N
    private static final int MAX_TOP_EQUIPOS = 3; // Guardaremos las 3 mejores opciones

//    private Map<String, Persona> personas;
    
//    private List<Equipo> topMejoresEquipos;
    
    private int contadorCasosBase;
    private int contadorPodas;
    
//    public CalculadorBacktracking(Map<String, Persona> personas) {
//        this.personas = personas;
//        this.topMejoresEquipos = new ArrayList<>();
//        this.contadorCasosBase = 0;
//        this.contadorPodas = 0;
//    }

    /**
     * Metodo que va a llamar el doInBackground() del SwingWorker.
     */
//    public List<Equipo> calcularMejoresEquipos() {
//        // Limpiamos estados por si se ejecuta más de una vez
//        topMejoresEquipos.clear();
//        contadorCasosBase = 0;
//        contadorPodas = 0;
//        Equipo solucionParcialInicial = new Equipo(); // Equipo vacío inicial
//        ejecutarBacktracking(0, solucionParcialInicial);
//        
//        return topMejoresEquipos;
//    }

//    private void ejecutarBacktracking(int indice, Equipo solucionParcial) {
//        if (debePodar(solucionParcial, indice)) {
//            contadorPodas++;
//            notificarProgreso();
//            return;
//        }
//        if (indice == personas.size()) {
//            contadorCasosBase++;
//            
//            if (esEquipoValido(solucionParcial)) {
//                evaluarYAgregarAlTop(solucionParcial);
//            }
//            
//            notificarProgreso();
//            return;
//        }
//        Persona personaActual = personas.get(indice);
//        solucionParcial.agregarJugador(personaActual);
//        ejecutarBacktracking(indice + 1, solucionParcial);
//        solucionParcial.remueveJugador(personaActual);
//        ejecutarBacktracking(indice + 1, solucionParcial);
//    }

//    private void evaluarYAgregarAlTop(Equipo solucionParcial) {
//        int puntajeNuevo = solucionParcial.getCalificacionTotal();
//        int i = 0;
//        while (i < topMejoresEquipos.size() && topMejoresEquipos.get(i).getCalificacionTotal() >= puntajeNuevo) {
//            i++;
//        }
//        if (i >= MAX_TOP_EQUIPOS) {
//            return; 
//        }
//        topMejoresEquipos.add(i, new Equipo(solucionParcial));
//
//        if (topMejoresEquipos.size() > MAX_TOP_EQUIPOS) {
//            topMejoresEquipos.remove(MAX_TOP_EQUIPOS);
//        }
//    }

//    private boolean debePodar(Equipo solucionParcial, int indice) {
//        if (topMejoresEquipos.size() < MAX_TOP_EQUIPOS) {
//            return false;
//        }
//
//        int puntajeMinimoASuperar = topMejoresEquipos.get(topMejoresEquipos.size() - 1).getCalificacionTotal();        
//        // Calculamos lo máximo que teóricamente podría sumar este camino
//        int loMaximoQuePuedeSumar = calcularRemanenteMaximo(indice);
//        if (solucionParcial.getCalificacionTotal() + loMaximoQuePuedeSumar <= puntajeMinimoASuperar) {
//            return true;
//        }
//
//        return false;
//    }

//    private int calcularRemanenteMaximo(int indice) {
//        int suma = 0;
//        for (int i = indice; i < personas.size(); i++) {
//			suma += personas.get(i).getCalificacion();
//		}
//        return suma;
//    }

//    private boolean esEquipoValido(Equipo equipo) {
//        // Agregar validaciones
//    	if (equipo == null)
//    		return false;
//    	//TODO hacer validación que en el equipo se encuentren todos los roles
//    	
//        return true; 
//    }

//    private void notificarProgreso() {
//        if (contadorCasosBase % 1000 == 0 || contadorPodas % 1000 == 0) {
//            int mejorPuntajeActual = topMejoresEquipos.isEmpty() ? 0 : topMejoresEquipos.get(0).getCalificacionTotal();
//            ProgresoEventoDto evento = new ProgresoEventoDto(contadorCasosBase, mejorPuntajeActual, contadorPodas);
//            notifyObservers(o -> o.alCambiarProgreso(evento));
//        }
//    }
}
