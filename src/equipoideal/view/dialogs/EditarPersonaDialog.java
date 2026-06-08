package equipoideal.view.dialogs;

import equipoideal.model.dto.PersonaDto;
import equipoideal.model.listener.PersonaListener;
import equipoideal.util.RolEnum;

public class EditarPersonaDialog extends PersonaDialog {

	private static final long serialVersionUID = 1L;

	public EditarPersonaDialog(String titulo) {
		super(titulo);

	}

	public void setPersonasListener(PersonaListener listener) {
		this.listener = listener;
	}

	@Override
	public void crearInputs() {

		btnAceptar.setText("Guardar cambios");
		
		crearFormularioPersonas();
		
		btnCargarDesde.setText("EditarFoto");
	}

	@Override
	public void accionesBoton() {

		btnAceptar.addActionListener(e -> {
			if (listener != null) {
				listener.onGuardarEdicionPersona();
			}
		});

		btnFoto.addActionListener(e -> cargarImagen());
		btnCargarDesde.addActionListener(e -> cargarImagen());
		
	}

	public void setPersona(PersonaDto dto) {

		txtNombre.setText(dto.getNombre());

		txtApellido.setText(dto.getApellido());

		spinnerPuntuacion.setValue(dto.getCalificacion());

		comboRol.setSelectedItem(dto.getRol());

		if (dto.getRutaFoto() != null) {

			mostrarImagen(dto.getRutaFoto());
		} else {
			btnCargarDesde.setVisible(false);
		}
	}
	
	@Override
	public PersonaDto getPersona() {
	    return new PersonaDto(
	            txtNombre.getText(), 
	            txtApellido.getText(), 
	            (int) spinnerPuntuacion.getValue(), 
	            (RolEnum) comboRol.getSelectedItem(),
	            this.rutaFoto);
	}

}
