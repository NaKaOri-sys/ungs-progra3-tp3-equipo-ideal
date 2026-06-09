package equipoideal.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import equipoideal.view.components.DialogStyleHelper;

public abstract class DialogPadre extends JDialog {
	private static final long serialVersionUID = 1L;

	protected JPanel panelPrincipal;
	protected JPanel panelSuperior;
	protected JPanel panelCentral;
	protected JPanel panelInferior;
	protected JPanel panelBotonesMedio;
	protected JPanel grillaBotones;
	protected JButton btnAceptar;
	protected JButton btnCerrar;
	protected JScrollPane scrollPane;
	protected JTable tabla;

	protected Color colorFondo =  new Color(15, 17, 24);

	public DialogPadre(String titulo) {

        setTitle(titulo);
        setModal(true);
        setSize(900, 650);
        setResizable(false);
        setLocationRelativeTo(null);

        inicializarVentana();
        inicializarPaneles();
        inicializarBotones();

        crearInputs();
        accionesBoton();
    }
	
	private void inicializarVentana() {

	    getContentPane().setBackground(colorFondo);
	    setLayout(new BorderLayout());
	}
	
	private void inicializarPaneles() {

	    panelPrincipal = new JPanel(new BorderLayout(0, 0));
	    panelPrincipal.setBackground(colorFondo);

	    panelSuperior = new JPanel();
	    panelSuperior.setOpaque(false);
	    
	    grillaBotones = crearPanel(new GridLayout(2, 2, 10, 10));
	    panelBotonesMedio = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
	    panelBotonesMedio.setOpaque(false);
	    panelBotonesMedio.add(grillaBotones);
	    
	    panelCentral = crearPanel(new BorderLayout(20, 20));
	    panelCentral.setOpaque(false);

	    panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
	    panelInferior.setOpaque(false);

	    panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

	    panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
	    panelPrincipal.add(panelCentral, BorderLayout.CENTER);
	    panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

	    add(panelPrincipal);
	}
	
	private void inicializarBotones() {

	    btnAceptar = crearBoton("Aceptar");
	    btnCerrar = crearBoton("Cerrar");
	    grillaBotones.add(btnAceptar);
	    grillaBotones.add(btnCerrar);
		
		panelCentral.add(panelBotonesMedio, BorderLayout.NORTH);

	    btnCerrar.addActionListener(e -> dispose());
	}

	protected void configurarTabla(String[] columnas, JPanel panel) {
	    DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
	        private static final long serialVersionUID = 1L;
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };
	    tabla = new JTable(modelo);
	    tabla.setRowSelectionAllowed(true);
	    tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    tabla.getTableHeader().setResizingAllowed(false);
	    tabla.getTableHeader().setReorderingAllowed(false); 
	    tabla.getTableHeader().setOpaque(false); 
	    
	    tabla.setBackground(DialogStyleHelper.ColorFondoPrincipal);
	    tabla.setFont(DialogStyleHelper.fuentePrincipal);
	    tabla.setForeground(DialogStyleHelper.ColorLetra);
	    scrollPane = new JScrollPane(tabla);
	    
	    tabla.getTableHeader().setFont(DialogStyleHelper.fuentePrincipal);
	    tabla.getTableHeader().setForeground(DialogStyleHelper.ColorLetra);
	    tabla.getTableHeader().setBackground(DialogStyleHelper.ColorFondoBotones);
	    
	    scrollPane.getViewport().setBackground(DialogStyleHelper.ColorFondoPrincipal);
	    
	    panel.add(scrollPane, BorderLayout.CENTER);
	}
	
	public void ventanaMensaje(String mensaje) {
		VentanaEmergente mensajePantalla = new VentanaEmergente(this, mensaje);
		mensajePantalla.setVisible(true);
	}
	
	protected JButton crearBoton(String nombre) {
	    return DialogStyleHelper.crearBotonEstilizado(nombre);
	}
	
	public static JPanel crearPanel(LayoutManager layout) {
	   return DialogStyleHelper.crearPanelEstilizado(layout, DialogStyleHelper.ColorBorde);
	}
	
	public static JPanel crearPanelConBordeEspecial(LayoutManager layout, Color colorBorde) {
	    return DialogStyleHelper.crearPanelEstilizado(layout, colorBorde);
	}
	
	protected static JLabel crearLabel(String texto, int tamanoLetra) {
	    return DialogStyleHelper.crearLabelEstilizado(texto, tamanoLetra);
	}
	
	protected JSpinner crearSpinner(int min, int max, int actual, int aumento) {
		return DialogStyleHelper.crearSpinnerEstilizado(min, max, actual, aumento);
	}
	protected JTextField crearTextField(int columna) {
		return DialogStyleHelper.crearTextFieldEstilizado(columna);
	}
	
	

	public abstract void crearInputs();
	public abstract void accionesBoton();
	public abstract void limpiarInputs();
}