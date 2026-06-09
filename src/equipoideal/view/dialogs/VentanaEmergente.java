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

    // Constructor común de un solo botón
    public VentanaEmergente(JDialog padre, String mensaje) {
        super(padre, "Alerta", true); 
        inicializarComponentes(mensaje, false);
        setLocationRelativeTo(padre);
    }
    
    
    public VentanaEmergente(JDialog padre, String mensaje, boolean esConfirmacion) {
        super(padre, "Confirmar Acción", true); 
        inicializarComponentes(mensaje, esConfirmacion);
        setLocationRelativeTo(padre);
    }
    
    private void inicializarComponentes(String mensaje, boolean esConfirmacion) { 
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

        // Panel inferior para los botones
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBoton.setOpaque(false);

        if (esConfirmacion) {
            JButton btnConfirmar = equipoideal.view.components.UiStyleHelper.crearBotonCustom("Confirmar", 
                new Color(220, 53, 69),
                new Color(200, 45, 60),
                Color.WHITE
            );
            btnConfirmar.setPreferredSize(new java.awt.Dimension(130, 36));
            btnConfirmar.addActionListener(e -> System.exit(0));

            JButton btnCancelar = equipoideal.view.components.UiStyleHelper.crearBotonCustom("Cancelar", 
                new Color(45, 50, 68),
                new Color(55, 62, 80),
                new Color(210, 220, 240)
            );
            btnCancelar.setPreferredSize(new java.awt.Dimension(130, 36));
            btnCancelar.addActionListener(e -> dispose());

            panelBoton.add(btnConfirmar);
            panelBoton.add(btnCancelar);
        } else {
            JButton btnEntendido = equipoideal.view.components.UiStyleHelper.crearBotonCustom("Entendido", 
                new Color(45, 50, 68),
                new Color(55, 62, 80),
                new Color(210, 220, 240)
            );
            btnEntendido.setPreferredSize(new java.awt.Dimension(130, 36));
            btnEntendido.addActionListener(e -> dispose()); 
            panelBoton.add(btnEntendido);
        }

        panelContenedor.add(panelBoton, BorderLayout.SOUTH);
        add(panelContenedor);
    }     
}