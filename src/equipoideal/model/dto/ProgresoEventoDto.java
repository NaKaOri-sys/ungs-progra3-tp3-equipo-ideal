package equipoideal.model.dto;

public class ProgresoEventoDto {
	private final int casosBaseProcesados;
    private final int mejorCalificacionActual;
    private final int nodosPodados;

    public ProgresoEventoDto(int casosBaseProcesados, int mejorCalificacionActual, int nodosPodados) {
        this.casosBaseProcesados = casosBaseProcesados;
        this.mejorCalificacionActual = mejorCalificacionActual;
        this.nodosPodados = nodosPodados;
    }
    
    public int getCasosBaseProcesados() {
        return casosBaseProcesados;
    }

    public int getMejorCalificacionActual() {
        return mejorCalificacionActual;
    }

    public int getNodosPodados() {
        return nodosPodados;
    }
}