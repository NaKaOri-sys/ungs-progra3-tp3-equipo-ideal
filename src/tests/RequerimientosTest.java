package tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import equipoideal.model.PersonaModel;
import equipoideal.model.RequerimientoModel;
import equipoideal.model.dto.PersonaDto;
import equipoideal.model.dto.RequerimientoDto;
import equipoideal.util.RolEnum;

public class RequerimientosTest {
	private RequerimientoModel modelo;
	private PersonaModel personaDialogModel;
	
	@Before
	public void setUp() throws IOException {
		personaDialogModel = new PersonaModel(null, null);
		modelo = new RequerimientoModel();
	}
	
	@Test
	public void crearRequerimientoDtoTest() {
		RequerimientoDto dto = new RequerimientoDto(1, 2, 3, 4);
		
		assertEquals(1, dto.getLideres());
		assertEquals(2, dto.getArquitectos());
		assertEquals(3, dto.getProgramadores());
		assertEquals(4, dto.getTesters());
	}
	
	@Test
	public void crearRequerimientosDesdeDTOTest() {
		
		PersonaDto persona1 = new PersonaDto("Leo", "Messi", 5, RolEnum.LIDER, "rutaFoto");
		personaDialogModel.agregarPersona(persona1);
		PersonaDto persona2 = new PersonaDto("Kylian", "Mbappe", 4, RolEnum.ARQUITECTO, "rutaFoto2");
		personaDialogModel.agregarPersona(persona2);
	    
	    RequerimientoDto reqDto = new RequerimientoDto(1, 1, 0, 0);
	    
	    modelo.crearRequerimientos(reqDto, personaDialogModel.getListaPersonas());
	    
	    Integer cantidadLideres = modelo.getRequerimientos().get(RolEnum.LIDER);
	    assertEquals(Integer.valueOf(1), cantidadLideres);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void requerimientosVacioTest() {
		RequerimientoDto dtoVacio = new RequerimientoDto(0, 0, 0, 0);
		modelo.crearRequerimientos(dtoVacio, personaDialogModel.getListaPersonas());
	}

	@Test(expected = IllegalArgumentException.class)
	public void masRequerimientosQuePersonasTest() {
		RequerimientoDto dto = new RequerimientoDto(1, 2, 1, 2);
		modelo.crearRequerimientos(dto, personaDialogModel.getListaPersonas());
	}

	@Test(expected = IllegalArgumentException.class)
	public void faltanLideresTest() {
		
		PersonaDto persona1 = new PersonaDto("Leo", "Messi", 5, RolEnum.ARQUITECTO, "ruta");
		PersonaDto persona2 = new PersonaDto("Cristiano", "Ronaldo", 4, RolEnum.ARQUITECTO, "ruta");
		personaDialogModel.agregarPersona(persona1);
		personaDialogModel.agregarPersona(persona2);
		
		RequerimientoDto dtoInvalido = new RequerimientoDto(2, 0, 0, 0);
		modelo.crearRequerimientos(dtoInvalido, personaDialogModel.getListaPersonas());
	}

	@Test(expected = IllegalArgumentException.class)
	public void faltanArquitectosTest() {
		PersonaDto persona1 = new PersonaDto("Leo", "Messi", 5, RolEnum.LIDER, "ruta");
		PersonaDto persona2 = new PersonaDto("Cristiano", "Ronaldo", 4, RolEnum.LIDER, "ruta");
		personaDialogModel.agregarPersona(persona1);
		personaDialogModel.agregarPersona(persona2);
		
		RequerimientoDto dtoInvalido = new RequerimientoDto(0, 3, 0, 0);
		modelo.crearRequerimientos(dtoInvalido, personaDialogModel.getListaPersonas());
	}

	@Test(expected = IllegalArgumentException.class)
	public void faltanProgramadoresTest() {
		PersonaDto persona1 = new PersonaDto("Leo", "Messi", 5, RolEnum.TESTER, "ruta");
		PersonaDto persona2 = new PersonaDto("Cristiano", "Ronaldo", 4, RolEnum.TESTER, "ruta");
		personaDialogModel.agregarPersona(persona1);
		personaDialogModel.agregarPersona(persona2);
		
		RequerimientoDto dtoInvalido = new RequerimientoDto(0, 0, 2, 0);
		modelo.crearRequerimientos(dtoInvalido, personaDialogModel.getListaPersonas());
	}

	@Test(expected = IllegalArgumentException.class)
	public void faltanTestersTest() {
		PersonaDto persona1 = new PersonaDto("Leo", "Messi", 5, RolEnum.PROGRAMADOR, "ruta");
		PersonaDto persona2 = new PersonaDto("Cristiano", "Ronaldo", 4, RolEnum.PROGRAMADOR, "ruta");
		personaDialogModel.agregarPersona(persona1);
		personaDialogModel.agregarPersona(persona2);
		
		RequerimientoDto dtoInvalido = new RequerimientoDto(0, 0, 0, 2);
		modelo.crearRequerimientos(dtoInvalido, personaDialogModel.getListaPersonas());
	}
	
}
