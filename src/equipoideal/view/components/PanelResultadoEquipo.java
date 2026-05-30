package equipoideal.view.components;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import equipoideal.model.dto.EquipoDto;
import equipoideal.model.dto.PersonaDto;
import equipoideal.model.dto.StatsDto;

public class PanelResultadoEquipo extends JPanel {
    private JPanel listaIntegrantes;
    private JLabel lblStats;

    public PanelResultadoEquipo(String titulo, Color colorBorde) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(colorBorde, 2), titulo));

        listaIntegrantes = new JPanel();
        listaIntegrantes.setLayout(new BoxLayout(listaIntegrantes, BoxLayout.Y_AXIS));
        
        add(new JScrollPane(listaIntegrantes), BorderLayout.CENTER);
        
        lblStats = new JLabel("Esperando resultados...");
        add(lblStats, BorderLayout.SOUTH);
    }
  
    public void actualizar(EquipoDto equipo, StatsDto stats) {
        listaIntegrantes.removeAll();
        for (PersonaDto p : equipo.getIntegrantes()) {
            listaIntegrantes.add(new FichaIntegrante(p));
        }
        lblStats.setText("<html>Tiempo: " + stats.getTiempo() + "ms | " + 
                         "Casos Base: " + stats.getCasosBase() + "</html>");
        revalidate();
        repaint();
    }
}