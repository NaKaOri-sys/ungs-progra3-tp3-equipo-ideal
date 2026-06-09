package equipoideal.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import equipoideal.model.dto.ResultadoComparativoDto;
import equipoideal.model.event.IObserverCalculador;

public class CalculadorSolucion {
	private CalculadorBacktracking backtracking;
	private CalculadorHeuristica heuristica;

	public CalculadorSolucion(CalculadorBacktracking backtracking, CalculadorHeuristica heuristica) {
		this.backtracking = backtracking;
		this.heuristica = heuristica;
	}

	public void addObserver(IObserverCalculador observer) {
		this.backtracking.addObserver(observer);
		this.heuristica.addObserver(observer);
	}

	public void removeObserver(IObserverCalculador observer) {
		if (this.backtracking != null) {
			this.backtracking.removeObserver(observer);
		}
		if (this.heuristica != null) {
			this.heuristica.removeObserver(observer);
		}
	}

	public ResultadoComparativoDto calcularSolucionGlobal() {
		ResultadoComparativoDto res = new ResultadoComparativoDto();
		res.setEquipoBacktracking(this.backtracking.calcularMejorEquipo());

		// La heuristica puede fallar por como está pensada con las incompatibilidades, por lo tanto se omite para mostrar un mensaje del porque fallo.
		try {
			res.setEquipoHeuristica(this.heuristica.ejecutarHeuristica());
		} catch (RuntimeException e) {
			// Se omite: el dashboard mostrara que la heuristica no encontro solucion
		}

		return res;
	}

	public static CalculadorSolucion crearDesdeModelos(PersonaModel pm, RequerimientoModel rm, IncompatibleModel im) {
		CalculadorBacktracking bt = new CalculadorBacktracking(new ArrayList<>(pm.getListaPersonas()),
				new LinkedHashMap<>(rm.getRequerimientos()), im.obtenerIncompatibilidades());
		CalculadorHeuristica h = new CalculadorHeuristica(new ArrayList<>(pm.getListaPersonas()),
				new LinkedHashMap<>(rm.getRequerimientos()), im.obtenerIncompatibilidades());
		return new CalculadorSolucion(bt, h);
	}
}
