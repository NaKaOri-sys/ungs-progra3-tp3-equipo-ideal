package equipoideal.model;

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
		res.setEquipoHeuristica(this.heuristica.ejecutarHeuristica());
		res.setEquipoBacktracking(this.backtracking.calcularMejorEquipo());

		return res;
	}
}