package equipoideal.view;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

import equipoideal.model.listener.IMenuListener;
import equipoideal.util.Observable;
import equipoideal.view.dialogs.VentanaEmergente;
import equipoideal.view.components.UiStyleHelper; 

public class MenuView extends JPanel {
    private static final long serialVersionUID = 1L;

    private JButton btnCargarPersona;
    private JButton btnRequerimiento;
    private JButton btnIncompatibilidad;
    private JButton btnBusqueda;
    private JButton btnExit;

    private URL urlCargarPersona;
    private URL urlRequerimiento;
    private URL urlIncompatibilidad;
    private URL urlBusqueda;

    private Observable<IMenuListener> observable;

    public MenuView() {
        this.observable = new Observable<>();
        initialize();
    }

    public void initialize() {
        setLayout(new BorderLayout(0, 0));
        setBackground(UiStyleHelper.BG_COLOR); 

        // header
        add(crearTopBar(), BorderLayout.NORTH);

        // contiene botones 
        JPanel panelCentral = new JPanel(null);
        panelCentral.setOpaque(false);


        btnCargarPersona = crearBoton("Cargar Personas");
        urlCargarPersona = getClass().getResource("/equipoideal/resources/Usuario.png");
        asignarIcono(btnCargarPersona, urlCargarPersona);
        btnCargarPersona.setBounds(70, 180, 190, 45); // Un poco más alto
        btnCargarPersona.addActionListener(e -> {observable.notifyObservers(listener -> listener.onCargarPersonas());});
        panelCentral.add(btnCargarPersona);

        
        btnRequerimiento = crearBoton("Requerimientos");
        urlRequerimiento = getClass().getResource("/equipoideal/resources/Requerimiento.png");
        asignarIcono(btnRequerimiento, urlRequerimiento);
        btnRequerimiento.setBounds(315, 180, 190, 45);
        btnRequerimiento.addActionListener(e -> {
            observable.notifyObservers(listener -> listener.onRequerimientos());
        });
        panelCentral.add(btnRequerimiento);

 
        btnIncompatibilidad = crearBoton("Incompatibilidad");
        urlIncompatibilidad = getClass().getResource("/equipoideal/resources/Incompatibilidad.png");
        asignarIcono(btnIncompatibilidad, urlIncompatibilidad);
        btnIncompatibilidad.setBounds(560, 180, 190, 45);
        btnIncompatibilidad.addActionListener(e -> {
            observable.notifyObservers(listener -> listener.onIncompatibilidad());
        });
        panelCentral.add(btnIncompatibilidad);


        btnBusqueda = crearBotonBusqueda("Buscar Equipo", UiStyleHelper.BTN_VERDE);           
        urlBusqueda = getClass().getResource("/equipoideal/resources/Busqueda.png");
        asignarIcono(btnBusqueda, urlBusqueda);
        btnBusqueda.setBounds(315, 270, 190, 45);
        btnBusqueda.addActionListener(e -> {
            observable.notifyObservers(listener -> listener.onBusqueda());
        });
        panelCentral.add(btnBusqueda);

        add(panelCentral, BorderLayout.CENTER);
    }

    private JPanel crearTopBar() {
        JPanel bar = new JPanel(new BorderLayout()) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(40, 46, 62)); 
                g2.fillRect(0, getHeight() - 1, getWidth(), 1);
                g2.dispose();
            }
        };
        bar.setBackground(new Color(20, 23, 33));
        bar.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        left.setOpaque(false);

        JLabel lblTitle = new JLabel("Equipo Ideal — Menú Principal");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitle.setForeground(new Color(230, 235, 245));
        left.add(lblTitle);

        // El botón salir
        btnExit = crearBotonSalir("Salir del Sistema →");
        btnExit.addActionListener(e -> confirmarSalida());

        bar.add(left, BorderLayout.WEST);
        bar.add(btnExit, BorderLayout.EAST);
        return bar;
    }

    // botones estándar redondeados con hover
    private JButton crearBoton(String texto) {
        return UiStyleHelper.crearBotonCustom(
            texto, 
            new Color(30, 34, 45),  
            new Color(45, 50, 68),  
            new Color(190, 200, 220) 
        );
    }

    private JButton crearBotonBusqueda(String texto, Color colorBase) {
        return UiStyleHelper.crearBotonCustom(
            texto, 
            colorBase,             
            colorBase.brighter(),  
            Color.WHITE             
        );
    }

    private JButton crearBotonSalir(String texto) {
        JButton btn = UiStyleHelper.crearBotonCustom(
            texto, 
            new Color(45, 50, 68),  
            new Color(220, 53, 69),
            new Color(230, 235, 245) 
        );
        
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        
        return btn;
    }

    private void asignarIcono(JButton boton, URL urlIcono) {
        if (urlIcono != null) {
            ImageIcon iconoGrande = new ImageIcon(urlIcono);
            Image imagenRedimensionada = iconoGrande.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imagenRedimensionada));
            boton.setIconTextGap(8); // Espaciado entre icono y texto
        }
    }

    private void confirmarSalida() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea salir?", "Confirmar Salida",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public void mostrarMensajeAdvertencia(String mensaje) {
        VentanaEmergente aviso = new VentanaEmergente(null, mensaje);
        aviso.setVisible(true);
    }

    public Observable<IMenuListener> obtenerObserver() {
        return this.observable;
    }
}