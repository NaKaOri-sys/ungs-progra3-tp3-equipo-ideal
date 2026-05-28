package equipoideal.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public abstract class DialogPadre extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JPanel panelPrincipal;
	protected JPanel panelInputs;
	protected JPanel panelBotones;
	protected JPanel panelLista;

    protected JButton btnAceptar;
    protected JButton btnCerrar;
//    protected JButton btnCargarDesde; 
    
    protected JScrollPane scrollPane;
    protected JTable tabla;
    
    protected  Color ColorFondo = Color.gray;
//    protected  Font Fuente = new Font
	 
	public DialogPadre(JFrame frame, String titulo) {
        super(frame, titulo, true);
        
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(ColorFondo);
        
        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(ColorFondo);

        panelInputs = new JPanel();
        panelInputs.setBackground(ColorFondo);

        panelBotones = new JPanel();
        panelBotones.setBackground(ColorFondo);

		panelBotones.setLayout(new GridLayout(2, 1, 0, 5));

        panelLista = new JPanel();
        panelLista.setBackground(ColorFondo);
        
        JPanel panelContenedorSuperior = new JPanel(new BorderLayout());
        panelContenedorSuperior.setOpaque(false); 

        panelContenedorSuperior.add(panelInputs, BorderLayout.NORTH);
        panelContenedorSuperior.add(panelBotones, BorderLayout.SOUTH);

        panelPrincipal.add(panelContenedorSuperior, BorderLayout.NORTH); 
        panelPrincipal.add(panelLista, BorderLayout.CENTER);             

        add(panelPrincipal);        

        btnAceptar = new JButton("Agregar");
        panelBotones.add(btnAceptar);

        btnCerrar = new JButton("Cerrar");
        panelBotones.add(btnCerrar);
        
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20,200,20,200));
		panelInputs.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));
        panelLista.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
        
        btnCerrar.addActionListener(e -> dispose());
        
        

//        btnCargarDesde = new JButton("Cargar Desde JSON");
//        panelBotones.add(btnCargarDesde);
	}
	protected void crearTabla() {
		String[] columnas = {"Nombre", "Apellido", "Puesto", "Puntos", "Incompatible con.."};
        
        DefaultTableModel modeloVacio = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloVacio);
        scrollPane = new JScrollPane(tabla);
        
        panelLista.setLayout(new BorderLayout());
        panelLista.add(scrollPane, BorderLayout.CENTER);
        panelLista.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
		
	}
	
	public abstract void crearInputs();

	public abstract void accionesBoton();
		
	
	

}
