package equipoideal.model.dto;

import equipoideal.util.OrigenCalculadorEnum;

public class ProgresoEventoDto {
	private final int casosBaseProcesados;
    private final long tiempo;
    private final int nodosPodados;
    private OrigenCalculadorEnum origen;

    public ProgresoEventoDto(int casosBaseProcesados, long tiempo, int nodosPodados, OrigenCalculadorEnum origen) {
        this.casosBaseProcesados = casosBaseProcesados;
        this.tiempo = tiempo;
        this.nodosPodados = nodosPodados;
        this.origen = origen;
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
    
    public OrigenCalculadorEnum getOrigen() {
    	return origen;
    }
}