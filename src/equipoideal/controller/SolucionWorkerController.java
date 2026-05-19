package equipoideal.controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;
import equipoideal.model.CalculadorBacktracking;
import equipoideal.model.dto.ProgresoEventoDto;
import equipoideal.model.event.IObserverBacktracking;
import equipoideal.view.LoadingSolutionPanel;

//TODO este swingWorker va a retornar el listado de equipos resultantes obtenidos en el worker
public class SolucionWorkerController extends SwingWorker<List<Void>, ProgresoEventoDto> implements IObserverBacktracking {
    
    private CalculadorBacktracking modelo;
    private LoadingSolutionPanel viewPanel;

    public SolucionWorkerController(CalculadorBacktracking modelo, LoadingSolutionPanel viewPanel) {
        this.modelo = modelo;
        this.viewPanel = viewPanel;
        
        this.modelo.addObserver(this); 
    }

    @Override
    protected List<Void> doInBackground() throws Exception {
        // Acá va el algoritmo de backtracking que corre en segundo hilo
//        return modelo.calcularEquipoIdeal();
        return new ArrayList<Void>();
    }

    @Override
    public void alCambiarProgreso(ProgresoEventoDto evento) {
        publish(evento); 
    }

    @Override
    protected void process(List<ProgresoEventoDto> chunks) {
        ProgresoEventoDto ultimoProgreso = chunks.get(chunks.size() - 1);
        
        viewPanel.actualizarEstadisticas(ultimoProgreso.getCasosBaseProcesados());
        viewPanel.actualizarMensaje("Mejor puntaje actual: " + ultimoProgreso.getMejorCalificacionActual());
    }

    @Override
    protected void done() {
        try {
            //TODO hay que llevar a la siguiente vista los equipos obtenidos
            viewPanel.finalizarCarga();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}