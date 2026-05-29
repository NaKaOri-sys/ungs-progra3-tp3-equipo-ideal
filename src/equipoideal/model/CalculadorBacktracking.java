package equipoideal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import equipoideal.model.dto.EquipoDto;
import equipoideal.model.dto.ProgresoEventoDto;
import equipoideal.model.event.IObserverCalculador;
import equipoideal.util.IndexCache;
import equipoideal.util.Observable;
import equipoideal.util.OrigenCalculadorEnum;
import equipoideal.util.RestriccionesPoda;
import equipoideal.util.SolutionValidator;

public class CalculadorBacktracking extends Observable<IObserverCalculador> {
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
		try {
			SolutionValidator.solutionValidator(personas, requerimientosRoles, matrizIncompatibilidades);
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
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
		notificarProgreso();
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
		return !(cantidadActual >= maximoPermitido) && !RestriccionesPoda.esIncompatibleConEquipo(indicePersona,
				solucionParcial, listaPersonas, matrizIncompatibilidades, this.cacheIndice.obtenerIndiceCache());
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
		long tiempoTranscurrido = tiempoActual - ultimoTiempoNotificado;
		if (tiempoTranscurrido > 100) {
			ultimoTiempoNotificado = tiempoActual;
			ProgresoEventoDto evento = new ProgresoEventoDto(contadorCasosBase, tiempoTranscurrido, contadorPodas,
					OrigenCalculadorEnum.BACKTRACKING);
			notifyObservers(o -> o.alCambiarProgreso(evento));
		}
	}
}