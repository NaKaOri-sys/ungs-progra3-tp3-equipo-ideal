package equipoideal.model.dto;

public class ProgresoEventoDto {
	private final int casosBaseProcesados;
    private final long tiempo;
    private final int nodosPodados;

    public ProgresoEventoDto(int casosBaseProcesados, long tiempo, int nodosPodados) {
        this.casosBaseProcesados = casosBaseProcesados;
        this.tiempo = tiempo;
        this.nodosPodados = nodosPodados;
    }
    
    public int getCasosBaseProcesados() {
        return casosBaseProcesados;
    }

    public int getNodosPodados() {
        return nodosPodados;
    }
    
    public long getTiempo() {
    	return tiempo;
    }
}