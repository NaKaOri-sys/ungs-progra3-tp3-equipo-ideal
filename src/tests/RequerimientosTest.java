package tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import equipoideal.model.PersonaDialogModel;
import equipoideal.model.Requerimiento;
import equipoideal.model.RequerimientosModel;
import equipoideal.model.dto.RequerimientosDto;
import equipoideal.util.RolEnum;

public class RequerimientosTest {
	private RequerimientosModel modelo;
	private PersonaDialogModel personaDialogModel;
	
	@Before
	public void setUp() throws IOException {
		personaDialogModel = new PersonaDialogModel(null, null);
		modelo = new RequerimientosModel(personaDialogModel);
	}
	
	@Test
	public void crearRequerimientoDtoTest() {
		RequerimientosDto dto = new RequerimientosDto(1, 2, 3, 4);
		
		assertEquals(1, dto.getLideres());
		assertEquals(2, dto.getArquitectos());
		assertEquals(3, dto.getProgramadores());
		assertEquals(4, dto.getTesters());
	}
	
	@Test
	public void crearObjetoRequerimientoTest() {
		Requerimiento req = new Requerimiento(RolEnum.PROGRAMADOR, 5);
		
		assertEquals(RolEnum.PROGRAMADOR, req.getRol());
		assertEquals(5, req.getCantidad());
	}
	
	@Test
	public void crearRequerimientosDesdeDTOTest() {
	    
	    
	    personaDialogModel.agregarPersona("Leo", "Messi", 5, RolEnum.LIDER, "ruta");
	    personaDialogModel.agregarPersona("Cristiano", "Ronaldo", 4, RolEnum.ARQUITECTO, "ruta");
	    
	    
	    
	    RequerimientosDto dto = new RequerimientosDto(1, 1, 0, 0);
	    
	    modelo.crearRequerimientos(dto);
	    
	    assertEquals(RolEnum.LIDER, modelo.getRequerimientos().get(0).getRol());
	    assertEquals(1, modelo.getRequerimientos().get(0).getCantidad());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void requerimientosVacioTest() {
		RequerimientosDto dtoVacio = new RequerimientosDto(0, 0, 0, 0);
		modelo.crearRequerimientos(dtoVacio);
	}

	@Test(expected = IllegalArgumentException.class)
	public void masRequerimientosQuePersonasTest() {
		RequerimientosDto dto = new RequerimientosDto(1, 2, 1, 2);
		modelo.crearRequerimientos(dto);
	}

	@Test(expected = IllegalArgumentException.class)
	public void faltanLideresTest() {
		
		personaDialogModel.agregarPersona("Leo", "Messi", 5, RolEnum.ARQUITECTO, "ruta");
		personaDialogModel.agregarPersona("Cristiano", "Ronaldo", 4, RolEnum.ARQUITECTO, "ruta");
		
		RequerimientosDto dtoInvalido = new RequerimientosDto(2, 0, 0, 0);
		modelo.crearRequerimientos(dtoInvalido);
	}

	@Test(expected = IllegalArgumentException.class)
	public void faltanArquitectosTest() {
		
		personaDialogModel.agregarPersona("Leo", "Messi", 5, RolEnum.LIDER, "ruta");
		personaDialogModel.agregarPersona("Cristiano", "Ronaldo", 4, RolEnum.LIDER, "ruta");
		
		RequerimientosDto dtoInvalido = new RequerimientosDto(0, 3, 0, 0);
		modelo.crearRequerimientos(dtoInvalido);
	}

	@Test(expected = IllegalArgumentException.class)
	public void faltanProgramadoresTest() {
		personaDialogModel.agregarPersona("Leo", "Messi", 5, RolEnum.TESTER, "ruta");
		personaDialogModel.agregarPersona("Cristiano", "Ronaldo", 4, RolEnum.TESTER, "ruta");
		
		RequerimientosDto dtoInvalido = new RequerimientosDto(0, 0, 2, 0);
		modelo.crearRequerimientos(dtoInvalido);
	}

	@Test(expected = IllegalArgumentException.class)
	public void faltanTestersTest() {
		
		personaDialogModel.agregarPersona("Leo", "Messi", 5, RolEnum.PROGRAMADOR, "ruta");
		personaDialogModel.agregarPersona("Cristiano", "Ronaldo", 4, RolEnum.PROGRAMADOR, "ruta");
		
		RequerimientosDto dtoInvalido = new RequerimientosDto(0, 0, 0, 2);
		modelo.crearRequerimientos(dtoInvalido);
	}
	
}
