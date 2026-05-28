package equipoideal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import equipoideal.model.dto.EquipoDto;
import equipoideal.model.dto.ProgresoEventoDto;
import equipoideal.model.event.IObserverBacktracking;
import equipoideal.util.IndexCache;
import equipoideal.util.Observable;
import equipoideal.util.RestriccionesPoda;
import equipoideal.util.SolutionValidator;

public class CalculadorBacktracking extends Observable<IObserverBacktracking> {
	private List<Persona> listaPersonas;
	private Map<String, Integer> requerimientosRoles;
	private boolean[][] matrizIncompatibilidades;
	private long ultimoTiempoNotificado = 0;

	private Equipo mejorEquipo;
	private int contadorCasosBase;
	private int contadorPodas;
	private IndexCache cacheIndice;

	public CalculadorBacktracking(List<Persona> personas, Map<String, Integer> requerimientosRoles,
			boolean[][] matrizIncompatibilidades) {
		SolutionValidator.solutionValidator(personas, requerimientosRoles, matrizIncompatibilidades);
		this.listaPersonas = new ArrayList<>(personas);
		this.requerimientosRoles = requerimientosRoles;
		this.matrizIncompatibilidades = matrizIncompatibilidades;
		this.mejorEquipo = new Equipo(new ArrayList<Persona>());
		this.contadorCasosBase = 0;
		this.contadorPodas = 0;
		this.cacheIndice = new IndexCache(this.listaPersonas);
	}

	public EquipoDto calcularMejorEquipo() {
		this.contadorCasosBase = 0;
		this.contadorPodas = 0;
		
		Equipo solucionParcialInicial = new Equipo(new ArrayList<Persona>());
		ejecutarBacktracking(0, solucionParcialInicial);
		return this.mejorEquipo.toDto();
	}

	private void ejecutarBacktracking(int indice, Equipo solucionParcial) {
		if (RestriccionesPoda.debePodar(solucionParcial, indice, mejorEquipo, listaPersonas)) {
			contadorPodas++;
			notificarProgreso();
			return;
		}
		if (indice == listaPersonas.size()) {
			contadorCasosBase++;
			if (cumpleExactamenteConLosRoles(solucionParcial)
					&& (solucionParcial.getCalificacionTotal() > this.mejorEquipo.getCalificacionTotal())) {
				this.mejorEquipo = new Equipo(new ArrayList<>(solucionParcial.obtenerIntegrantes()));
			}
			notificarProgreso();
			return;
		}

		Persona personaActual = listaPersonas.get(indice);
		if (esPosibleAgregar(indice, personaActual, solucionParcial)) {
			solucionParcial.obtenerIntegrantes().add(personaActual);
			ejecutarBacktracking(indice + 1, solucionParcial);
			solucionParcial.obtenerIntegrantes().remove(personaActual);
		}
		ejecutarBacktracking(indice + 1, solucionParcial);
	}

	private boolean esPosibleAgregar(int indicePersona, Persona persona, Equipo solucionParcial) {
		int maximoPermitido = requerimientosRoles.getOrDefault(persona.getRol(), 0);
		int cantidadActual = solucionParcial.getCantidadPorRol(persona.getRol());
		if (cantidadActual >= maximoPermitido) {
			return false;
		}

		if (RestriccionesPoda.esIncompatibleConEquipo(indicePersona, solucionParcial, listaPersonas, matrizIncompatibilidades, this.cacheIndice.obtenerIndiceCache())) {
			return false;
		}

		return true;
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

	private void notificarProgreso() {
		long tiempoActual = System.currentTimeMillis();
		if (tiempoActual - ultimoTiempoNotificado > 100) {
			ultimoTiempoNotificado = tiempoActual;
			// TODO: Pasar el tiempo transcurrido desde el inicio, no el timestamp actual (ultimoTiempoNotificado es timestamp)
			ProgresoEventoDto evento = new ProgresoEventoDto(contadorCasosBase, ultimoTiempoNotificado, contadorPodas);
			notifyObservers(o -> o.alCambiarProgreso(evento));
		}
	}
}