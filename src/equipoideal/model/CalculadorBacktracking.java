package equipoideal.model;

import java.util.ArrayList;
import java.util.List;

import equipoideal.model.dto.EquipoDto;
import equipoideal.model.dto.ProgresoEventoDto;
import equipoideal.model.event.IObserverCalculador;
import equipoideal.util.EquipoCalculadorUtil;
import equipoideal.util.IndexCache;
import equipoideal.util.Observable;
import equipoideal.util.OrigenCalculadorEnum;
import equipoideal.util.RestriccionesPoda;
import equipoideal.util.SolutionValidator;

public class CalculadorBacktracking extends Observable<IObserverCalculador> {
	private List<Persona> listaPersonas;
	private List<Requerimiento> requerimientos;
	private boolean[][] matrizIncompatibilidades;
	private long tiempoInicio = 0;
	private long ultimoTiempoNotificado = 0;
	private Equipo mejorEquipo;
	private int contadorCasosBase;
	private int contadorPodas;
	private IndexCache cacheIndice;

	public CalculadorBacktracking(List<Persona> personas, List<Requerimiento> requerimientosRoles,
			boolean[][] matrizIncompatibilidades) {
		SolutionValidator.solutionValidator(personas, requerimientosRoles, matrizIncompatibilidades);
		this.listaPersonas = new ArrayList<>(personas);
		this.requerimientos = new ArrayList<>(requerimientosRoles);
		this.matrizIncompatibilidades = matrizIncompatibilidades;
		this.mejorEquipo = new Equipo(new ArrayList<Persona>());
		this.contadorCasosBase = 0;
		this.contadorPodas = 0;
		this.cacheIndice = new IndexCache(this.listaPersonas);
	}

	public EquipoDto calcularMejorEquipo() {
		this.contadorCasosBase = 0;
		this.contadorPodas = 0;
		this.tiempoInicio = EquipoCalculadorUtil.obtenerTiempoActual();
		this.ultimoTiempoNotificado = this.tiempoInicio;
		Equipo solucionParcialInicial = new Equipo(new ArrayList<Persona>());
		ejecutarBacktracking(0, solucionParcialInicial);
		notificarObserver(contadorCasosBase, EquipoCalculadorUtil.obtenerTiempoActual(), tiempoInicio, contadorPodas);
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
		int cantidadActual = solucionParcial.getCantidadPorRol((persona.getRol()));
		return EquipoCalculadorUtil.esPosibleAgregar((persona.getRol()), cantidadActual, requerimientos,
				!RestriccionesPoda.esIncompatibleConEquipo(indicePersona, solucionParcial, listaPersonas,
						matrizIncompatibilidades, this.cacheIndice.obtenerIndiceCache()));
	}

	private boolean cumpleExactamenteConLosRoles(Equipo equipo) {
		return EquipoCalculadorUtil.cumpleConLosRequerimientos(equipo, requerimientos);
	}

	private void notificarProgreso() {
		long tiempoActual = EquipoCalculadorUtil.obtenerTiempoActual();
		if (EquipoCalculadorUtil.debeNotificarProgreso(tiempoActual, ultimoTiempoNotificado, 100)) {
			ultimoTiempoNotificado = tiempoActual;
			notificarObserver(contadorCasosBase, tiempoActual, tiempoInicio, contadorPodas);
			
		}
	}

	private void notificarObserver(int contadorCasosBase, long tiempoActual, long tiempoInicio, int contadorPodas) {
		ProgresoEventoDto evento = new ProgresoEventoDto(contadorCasosBase, tiempoActual - tiempoInicio,
				contadorPodas, OrigenCalculadorEnum.BACKTRACKING);
		notifyObservers(o -> o.alCambiarProgreso(evento));
	}
}