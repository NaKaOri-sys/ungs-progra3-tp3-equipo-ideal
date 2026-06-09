package equipoideal.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import equipoideal.view.components.DialogStyleHelper;

public class VentanaEmergente extends JDialog {
    private static final long serialVersionUID = 1L;

    public VentanaEmergente(JDialog padre, String mensaje) {
        super(padre, "Alerta", true); 
        inicializarComponentes(mensaje);
        setLocationRelativeTo(padre);
    }
    
    private void inicializarComponentes(String mensaje) { 
    	setSize(600, 180);
        setLayout(new BorderLayout());
        setResizable(false);
        
        getContentPane().setBackground(DialogStyleHelper.ColorFondoPrincipal);

        JPanel panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setOpaque(false); 
        panelContenedor.setBorder(new EmptyBorder(25, 25, 15, 25));

        JLabel lblMensaje = DialogStyleHelper.crearLabelEstilizado(mensaje, 13);
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        panelContenedor.add(lblMensaje, BorderLayout.CENTER);


        JButton btnEntendido = equipoideal.view.components.UiStyleHelper.crearBotonCustom( "Entendido", 
            new Color(45, 50, 68),
            new Color(55, 62, 80),
            new Color(210, 220, 240)
        );
        
        //tamaño para que no se deforme
        btnEntendido.setPreferredSize(new java.awt.Dimension(130, 36));
        btnEntendido.addActionListener(e -> dispose()); 

        // Panel inferior para centrar el botón
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setOpaque(false);
        panelBoton.add(btnEntendido);
        panelContenedor.add(panelBoton, BorderLayout.SOUTH);

        add(panelContenedor);
    }     
}