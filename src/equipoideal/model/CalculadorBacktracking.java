package equipoideal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import equipoideal.model.dto.ProgresoEventoDto;
import equipoideal.model.event.IObserverBacktracking;
import equipoideal.util.Observable;

public class CalculadorBacktracking extends Observable<IObserverBacktracking> {
	private List<Persona> listaPersonas;
	private Map<String, Integer> requerimientosRoles;
	private boolean[][] matrizIncompatibilidades;
	private long ultimoTiempoNotificado = 0;
	
	private Equipo mejorEquipo;
	private int contadorCasosBase;
	private int contadorPodas;

	public CalculadorBacktracking(List<Persona> personas, Map<String, Integer> requerimientosRoles,
			boolean[][] matrizIncompatibilidades) {
		this.listaPersonas = new ArrayList<>(personas);
		this.requerimientosRoles = requerimientosRoles;
		this.matrizIncompatibilidades = matrizIncompatibilidades;
		this.mejorEquipo = new Equipo();
		this.contadorCasosBase = 0;
		this.contadorPodas = 0;
	}

	public Equipo calcularMejoresEquipos() {
		this.contadorCasosBase = 0;
		this.contadorPodas = 0;

		Equipo solucionParcialInicial = new Equipo();
		ejecutarBacktracking(0, solucionParcialInicial);

		return this.mejorEquipo;
	}

	private void ejecutarBacktracking(int indice, Equipo solucionParcial) {
		if (debePodar(solucionParcial, indice)) {
			contadorPodas++;
			notificarProgreso();
			return;
		}
		if (indice == listaPersonas.size()) {
			contadorCasosBase++;
			if (cumpleExactamenteConLosRoles(solucionParcial) && (solucionParcial.getCalificacionTotal() > this.mejorEquipo.getCalificacionTotal())) {
				this.mejorEquipo = new Equipo(solucionParcial);
			}
			notificarProgreso();
			return;
		}

		Persona personaActual = listaPersonas.get(indice);
		if (esPosibleAgregar(indice, personaActual, solucionParcial)) {
			solucionParcial.agregarJugador(personaActual);
			ejecutarBacktracking(indice + 1, solucionParcial);
			solucionParcial.remueveJugador(personaActual);
		}
		ejecutarBacktracking(indice + 1, solucionParcial);
	}

	private boolean esPosibleAgregar(int indicePersona, Persona persona, Equipo solucionParcial) {
		int maximoPermitido = requerimientosRoles.getOrDefault(persona.getRol(), 0);
		int cantidadActual = solucionParcial.getCantidadPorRol(persona.getRol());
		if (cantidadActual >= maximoPermitido) {
			return false;
		}

		if (esIncompatibleConEquipo(indicePersona, solucionParcial)) {
			return false;
		}

		return true;
	}

	private boolean esIncompatibleConEquipo(int indicePersona, Equipo solucionMomentanea) {
		boolean esIncompatible = false;
		for (Persona integrante : solucionMomentanea.getIntegrantes()) {
			int indiceIntegrante = integrante.getIndice();
			esIncompatible = esIncompatible || matrizIncompatibilidades[indicePersona][indiceIntegrante] == true;
		}
		return esIncompatible;
	}

	private boolean cumpleExactamenteConLosRoles(Equipo equipo) {
		for (String rol : requerimientosRoles.keySet()) {
			int requerido = requerimientosRoles.get(rol);
			if (equipo.getCantidadPorRol(rol) != requerido) {
				return false;
			}
		}
		return true;
	}

	private boolean debePodar(Equipo solucionParcial, int indice) {
		int puntajePosible = solucionParcial.getCalificacionTotal() + calcularRemanenteMaximo(indice);
	    if (puntajePosible <= mejorEquipo.getCalificacionTotal()) {
	        return true;
	    }
		return false;
	}

	private int calcularRemanenteMaximo(int indice) {
		int suma = 0;
		for (int i = indice; i < listaPersonas.size(); i++) {
			suma += listaPersonas.get(i).getCalificacion();
		}
		return suma;
	}

	private void notificarProgreso() {
		long tiempoActual = System.currentTimeMillis();
		if (tiempoActual - ultimoTiempoNotificado > 100) { 
	        ultimoTiempoNotificado = tiempoActual;
			int mejorPuntajeActual = this.mejorEquipo == null ? 0 : this.mejorEquipo.getCalificacionTotal();
			ProgresoEventoDto evento = new ProgresoEventoDto(contadorCasosBase, mejorPuntajeActual, contadorPodas);
			notifyObservers(o -> o.alCambiarProgreso(evento));
		}
	}
}