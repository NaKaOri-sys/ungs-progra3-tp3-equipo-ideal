package equipoideal.view.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VentanaEmergente extends JDialog {
    private static final long serialVersionUID = 1L;

    public VentanaEmergente(JDialog padre, String mensaje) {
        super(padre, "Error", true); // true = Modal 
        inicializarComponentes(mensaje);
        setLocationRelativeTo(padre);
    }


    private void inicializarComponentes(String mensaje) {
        setSize(350, 150);
        setLayout(new BorderLayout());

        // (padding)
        JPanel panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Texto del mensaje de error
        JLabel lblMensaje = new JLabel(mensaje);
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        panelContenedor.add(lblMensaje, BorderLayout.CENTER);

        // Botón para cerrar 
        JButton btnEntendido = new JButton("Entendido");
        btnEntendido.addActionListener(e -> dispose()); 

        // Panel del boton de abajo
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(btnEntendido);
        panelContenedor.add(panelBoton, BorderLayout.SOUTH);

        add(panelContenedor);
    }
}