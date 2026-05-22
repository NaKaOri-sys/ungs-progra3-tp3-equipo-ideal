package equipoideal.model.dto;

public class ResultadoComparativoDto {
	private EquipoDto equipoBacktracking;
	private EquipoDto equipoHeuristica;
	private StatsDto statsBacktracking;
	private StatsDto statsHeuristica;

	public EquipoDto getEquipoBacktracking() {
		return this.equipoBacktracking;
	}

	public StatsDto getStatsBacktracking() {
		return this.statsBacktracking;
	}

	public void setEquipoBacktracking(EquipoDto equipoBacktracking) {
		this.equipoBacktracking = equipoBacktracking;
	}

	public void setStatsBacktracking(StatsDto statsBacktracking) {
		this.statsBacktracking = statsBacktracking;
	}

	public EquipoDto getEquipoHeuristica() {
		return this.equipoHeuristica;
	}

	public StatsDto getStatsHeuristica() {
		return this.statsHeuristica;
	}

	public void setEquipoHeuristica(EquipoDto equipoHeuristica) {
		this.equipoHeuristica = equipoHeuristica;
	}

	public void setStatsHeuristica(StatsDto statsHeuristica) {
		this.statsHeuristica = statsHeuristica;
	}

}
