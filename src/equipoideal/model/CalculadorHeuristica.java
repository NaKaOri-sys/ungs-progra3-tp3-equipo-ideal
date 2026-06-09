package equipoideal.model;

import equipoideal.model.dto.EquipoDto;
import equipoideal.model.dto.ProgresoEventoDto;
import equipoideal.model.event.IObserverCalculador;
import equipoideal.util.EquipoCalculadorUtil;
import equipoideal.util.Observable;
import equipoideal.util.OrigenCalculadorEnum;
import equipoideal.util.RolEnum;
import equipoideal.util.SolutionValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CalculadorHeuristica extends Observable<IObserverCalculador> {

	private List<Persona> listaPersonas;
	private LinkedHashMap<RolEnum, Integer> requerimientos;
	private long tiempoInicio = 0;
	private Map<Persona, Set<Persona>> incompatibilidades;

	public CalculadorHeuristica(List<Persona> listaPersonas, LinkedHashMap<RolEnum, Integer> requerimientos,
			Map<Persona, Set<Persona>> incompatibilidades) {
		SolutionValidator.solutionValidator(listaPersonas, requerimientos, incompatibilidades);
		this.listaPersonas = new ArrayList<>(listaPersonas);
		this.requerimientos = new LinkedHashMap<RolEnum, Integer>(requerimientos);
		this.incompatibilidades = incompatibilidades;
	}

	public EquipoDto ejecutarHeuristica() {
		this.tiempoInicio = EquipoCalculadorUtil.obtenerTiempoActual();
		Equipo equipoResultante = new Equipo(new ArrayList<Persona>());
		Collections.sort(listaPersonas, (p1, p2) -> p2.compareTo(p1));
		try {
			for (Persona personaActual : listaPersonas) {
				if (esPosibleAgregar(personaActual, equipoResultante))
					equipoResultante.obtenerIntegrantes().add(personaActual);
				if (cumpleConTodosLosRequerimientos(equipoResultante)) {
					break;
				}
			}
			if (equipoResultante.obtenerIntegrantes().isEmpty() || !cumpleConTodosLosRequerimientos(equipoResultante)) {
				throw new RuntimeException("No se encontró un equipo que cumpla con los requerimientos.");
			}
			notificarObserver(EquipoCalculadorUtil.obtenerTiempoActual(), tiempoInicio);
			return equipoResultante.toDto();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private boolean esPosibleAgregar(Persona persona, Equipo equipoParcial) {
		RolEnum rol = (persona.getRol());
		int cantidadActual = equipoParcial.getCantidadPorRol((persona.getRol()));
		return EquipoCalculadorUtil.esPosibleAgregar(rol, cantidadActual, requerimientos,
				!(esIncompatibleConEquipo(persona, equipoParcial)));
	}

	private boolean esIncompatibleConEquipo(Persona personaActual, Equipo equipoParcial) {
		return EquipoCalculadorUtil.esIncompatibleConEquipo(personaActual, equipoParcial, incompatibilidades);
	}

	private boolean cumpleConTodosLosRequerimientos(Equipo equipo) {
		return EquipoCalculadorUtil.cumpleConLosRequerimientos(equipo, requerimientos);
	}

	private void notificarObserver(long tiempoActual, long tiempoInicio) {
		ProgresoEventoDto evento = new ProgresoEventoDto(0, tiempoActual - tiempoInicio, 0,
				OrigenCalculadorEnum.HEURISTICA);
		notifyObservers(o -> o.alCambiarProgreso(evento));
	}
}
