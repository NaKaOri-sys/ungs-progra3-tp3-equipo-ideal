package equipoideal.model;

import equipoideal.model.dto.EquipoDto;
import equipoideal.model.dto.ProgresoEventoDto;
import equipoideal.model.event.IObserverCalculador;
import equipoideal.util.EquipoCalculadorUtil;
import equipoideal.util.IndexCache;
import equipoideal.util.Observable;
import equipoideal.util.OrigenCalculadorEnum;
import equipoideal.util.RolEnum;
import equipoideal.util.SolutionValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalculadorHeuristica extends Observable<IObserverCalculador> {

	private List<Persona> listaPersonas;
	private List<Requerimiento> requerimientos;
	private boolean[][] matrizIncompatibilidades;
	private List<Persona> listaOrdenadaPersonas;
	private IndexCache cacheIndice;
	private long tiempoInicio = 0;

	public CalculadorHeuristica(List<Persona> listaPersonas, List<Requerimiento> requerimientos,
			boolean[][] matrizIncompatibilidades) {
		SolutionValidator.solutionValidator(listaPersonas, requerimientos, matrizIncompatibilidades);
		this.listaPersonas = new ArrayList<>(listaPersonas);
		this.listaOrdenadaPersonas = new ArrayList<>(listaPersonas);
		this.requerimientos = new ArrayList<>(requerimientos);
		this.matrizIncompatibilidades = matrizIncompatibilidades;
	}

	public EquipoDto ejecutarHeuristica() {
		this.tiempoInicio = EquipoCalculadorUtil.obtenerTiempoActual();
		this.cacheIndice = new IndexCache(listaPersonas);
		Equipo equipoResultante = new Equipo(new ArrayList<Persona>());
		Collections.sort(listaOrdenadaPersonas, (p1, p2) -> p2.compareTo(p1));
		try {
			for (Persona personaActual : listaOrdenadaPersonas) {
				if (esPosibleAgregar(personaActual, equipoResultante))
					equipoResultante.obtenerIntegrantes().add(personaActual);
				if (cumpleConTodosLosRequerimientos(equipoResultante)) {
					break;
				}
			}
			notificarObserver(EquipoCalculadorUtil.obtenerTiempoActual(), tiempoInicio);
			return equipoResultante.toDto();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private boolean esPosibleAgregar(Persona persona, Equipo equipoParcial) {
		RolEnum rol = (persona.getRol());
		int cantidadActual = equipoParcial.getCantidadPorRol((persona.getRol()));
		return EquipoCalculadorUtil.esPosibleAgregar(rol, cantidadActual, requerimientos,
				!(esIncompatibleConEquipo(persona, equipoParcial)));
	}

	private boolean esIncompatibleConEquipo(Persona personaActual, Equipo equipoParcial) {
		return EquipoCalculadorUtil.esIncompatibleConEquipo(personaActual, equipoParcial, matrizIncompatibilidades,
				this.cacheIndice.obtenerIndiceCache());
	}

	private boolean cumpleConTodosLosRequerimientos(Equipo equipo) {
		return EquipoCalculadorUtil.cumpleConLosRequerimientos(equipo, requerimientos);
	}

	private void notificarObserver(long tiempoActual, long tiempoInicio) {
		ProgresoEventoDto evento = new ProgresoEventoDto(0, tiempoActual - tiempoInicio,
				0, OrigenCalculadorEnum.HEURISTICA);
		notifyObservers(o -> o.alCambiarProgreso(evento));
	}
}
