package equipoideal.view.components;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import equipoideal.model.dto.PersonaDto;

public class FichaIntegrante extends JPanel {
    public FichaIntegrante(PersonaDto persona) {
        setLayout(new BorderLayout(10, 0));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel lblFoto = new JLabel(new ImageIcon(persona.getRutaFoto())); 
        add(lblFoto, BorderLayout.WEST);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(new JLabel("<html><b>" + persona.getNombre() + "</b></html>"));
        infoPanel.add(new JLabel("Rol: " + persona.getRol() + " | Cal: " + persona.getCalificacion()));
        
        add(infoPanel, BorderLayout.CENTER);
    }
}