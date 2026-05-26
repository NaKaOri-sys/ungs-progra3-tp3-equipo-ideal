package equipoideal.view.dialogs;

import javax.swing.JFrame;

public class VerEmpleadoDialog extends PersonasDialog {
    
    private String nombre;                                                                                                
    private String apellido;
    private String puesto;
    private String puntos;


    public VerEmpleadoDialog(JFrame frame, String nombre, String apellido, String puesto, String puntos) {
        super(frame, "Ficha del Integrante");
        
        this.nombre = nombre;
        this.apellido = apellido;
        this.puesto = puesto;
        this.puntos = puntos;
        
        crearInputs();         
        configurarModoLectura();
    }

    private void configurarModoLectura() {
        // Le clavo los datos que de la fila seleccionada
        txtNombre.setText(nombre);
        txtApellido.setText(apellido);
        txtRol.setText(puesto);
        txtPuntuacion.setText(puntos);
        
        // deberia bloquar los cuadros de texto para que el usuario no pueda editar nada
        txtNombre.setEditable(false);
        txtApellido.setEditable(false);
        txtRol.setEditable(false);
        txtPuntuacion.setEditable(false);
        
        // desactivo botones y funciones que no necesito
        btnFoto.setVisible(false);
        btnCargarDesde.setVisible(false);
        
        //los deje dentro de un if por si cambian algo del padre
        if (scrollPane != null) {
            scrollPane.setVisible(false);
        }
        if (panelLista != null) {
            panelLista.setVisible(false);
        }
        if (btnAceptar != null) {
        	btnAceptar.setVisible(false);
        }
       
     
        setSize(600, 500);
        setLocationRelativeTo(getOwner());
    }

    // Sobrescribo la accion del botón para que no intente agregar una persona al modelo, en su lugar que se cierre
    @Override
    public void accionesBoton() {
        if (btnAceptar != null) {
            btnAceptar.addActionListener(e -> dispose());
        }
    }
}