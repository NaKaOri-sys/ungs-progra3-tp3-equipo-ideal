package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import equipoideal.model.Persona;
import equipoideal.model.PersonaModel;
import equipoideal.model.dto.PersonaDto;
import equipoideal.model.repository.PersonaRepository;
import equipoideal.model.repository.PersonaRepositoryJson;
import equipoideal.util.RolEnum;

public class PersonasTest {
	private ArrayList<Persona> personas;
	private Persona persona1;
	private Persona persona2;
	private Persona persona3;
	
	private PersonaModel modelo;
	private PersonaRepository personaRepository;
	private File archivoJsonTemporal;
	private File carpetaFotosTemporal;
	
	@Rule
	public TemporaryFolder carpetaTemporal = new TemporaryFolder();
	
	@Before
	public void setUp() throws IOException {
		
		this.archivoJsonTemporal = carpetaTemporal.newFile("personas_test.json");
		this.carpetaFotosTemporal = carpetaTemporal.newFolder("carpeta_fotos");
		this.personaRepository = new PersonaRepositoryJson(archivoJsonTemporal.getAbsolutePath());
		this.modelo = new PersonaModel(personaRepository, carpetaFotosTemporal.getAbsolutePath());
		
		personas = new ArrayList<>();
		persona1 = new Persona("Leo", "Messi", 5, RolEnum.PROGRAMADOR, "ruta");
		persona2 = new Persona("Cristiano", "Ronaldo", 4, RolEnum.ARQUITECTO,  "ruta");
		persona3 = new Persona("Kylian", "Mbappe", 4, RolEnum.TESTER,  "ruta");
		
		personas.add(persona1);
		personas.add(persona2);
		personas.add(persona3);
	}
		
	//TEST CLASE PERSONA
	@Test
	public void crearPersonaTest() {
		assertEquals("Leo", persona1.getNombre());

	    assertEquals("Messi", persona1.getApellido());

	    assertEquals(5, persona1.getCalificacion());

	    assertEquals(RolEnum.PROGRAMADOR, persona1.getRol());
	}
	
	@Test
	public void compareToMayorTest() {
		assertEquals(1, persona1.compareTo(persona2));
	}
	
	@Test
	public void compareToMenorTest() {
		assertEquals(-1, persona2.compareTo(persona1));
	}
	
	@Test
	public void compareToIgualTest() {
		assertEquals(0, persona2.compareTo(persona3));
	}
	
	@Test
	public void crearDtoTest() {
		PersonaDto dto = persona1.toDto();

	    assertEquals("Leo", dto.getNombre());

	    assertEquals("Messi", dto.getApellido());

	    assertEquals(5, dto.getCalificacion());

	    assertEquals(RolEnum.PROGRAMADOR, dto.getRol());
	}
	
	
	@Test
	public void equalsPersonaTest() {
		Persona clonMessi = new Persona("Leo", "Messi", 1, RolEnum.TESTER, "ruta");
		
		assertEquals(true, persona1.equals(clonMessi));
		assertEquals(false, persona1.equals(persona2));
	}
	
	
	
	//Test PersonaDialogModel
	
	@Test
	public void agregarPersonaTest() {
		PersonaDto dto = new PersonaDto("Leo", "Messi", 5, RolEnum.PROGRAMADOR, "rutaFoto");
		modelo.agregarPersona(dto);
		Persona p = modelo.getListaPersonas().get(0);
		
		assertEquals(1, modelo.getListaPersonas().size());

	    assertEquals("Leo", p.getNombre());

	    assertEquals("Messi", p.getApellido());

	    assertEquals(5, p.getCalificacion());

	    assertEquals(RolEnum.PROGRAMADOR, p.getRol());
	    
	    assertEquals("rutaFoto", p.getRutaFoto());
	}
	
	@Test
	public void guardarEnJsonTest() {
		PersonaDto dto = new PersonaDto("Leo", "Messi", 5, RolEnum.PROGRAMADOR, "rutaFoto");
		modelo.agregarPersona(dto);
		PersonaDto dto2 = new PersonaDto("Kylian", "Mbappe", 4, RolEnum.TESTER, "rutaFoto2");
		modelo.agregarPersona(dto2);
		
		modelo.guardarPersonaEnJSON();
		
		ArrayList<Persona> personasGuardadas = personaRepository.loadAll(archivoJsonTemporal.getAbsolutePath());
		
		assertEquals(2, personasGuardadas.size());
		
		Persona primera = personasGuardadas.get(0);
	    assertEquals("Leo", primera.getNombre());
	    assertEquals("Messi", primera.getApellido());
	    assertEquals(5, primera.getCalificacion());
	    assertEquals(RolEnum.PROGRAMADOR, primera.getRol());
		
	}
	
	@Test
	public void cargarPersonasDesdeJsonTest() {
		personaRepository.saveAll(personas);
		modelo.cargarDesdeJSON(archivoJsonTemporal.getAbsolutePath());
		
		ArrayList<Persona> personasGuardadas = modelo.getListaPersonas();		
		Persona primera = personasGuardadas.get(0);
		
		assertEquals("Leo", primera.getNombre());
	    assertEquals("Messi", primera.getApellido());
	    assertEquals(5, primera.getCalificacion());
	    assertEquals(RolEnum.PROGRAMADOR, primera.getRol());
	}

	@Test
	public void guardarFotoTest() throws IOException {
		File fotoOrigen = carpetaTemporal.newFile("origen.jpg");
		
		String rutaDestino = modelo.guardarFoto(fotoOrigen);
		File archivoCopiado = new File(rutaDestino);
		
		
		assertEquals(carpetaFotosTemporal.getAbsolutePath(), archivoCopiado.getParentFile().getAbsolutePath());
		
	}
	
	@Test
	public void eliminarPersonaTest() {
		modelo.agregarPersona(new PersonaDto("Leo", "Messi", 5, RolEnum.PROGRAMADOR, "foto"));
		modelo.agregarPersona(new PersonaDto("Cristiano", "Ronaldo", 4, RolEnum.ARQUITECTO, "foto"));
		
		modelo.eliminarPersona(0);
		
		assertEquals(1, modelo.getListaPersonas().size());
		assertEquals("Cristiano", modelo.getListaPersonas().get(0).getNombre());
	}
	
	@Test
	public void editarPersonaCorrectamenteTest() {
		modelo.agregarPersona(new PersonaDto("Leo", "Messi", 5, RolEnum.PROGRAMADOR, "foto"));
		
		PersonaDto dtoEditado = new PersonaDto("Lionel", "Messi", 5, RolEnum.PROGRAMADOR, "foto_nueva");
		modelo.editarPersona(0, dtoEditado);
		
		Persona editada = modelo.getListaPersonas().get(0);
		
		assertEquals("Lionel", editada.getNombre());
		assertEquals("foto_nueva", editada.getRutaFoto());
	}
	

	@Test
	public void exportarJsonTest() {
	PersonaDto dto = new PersonaDto("Leo", "Messi", 5, RolEnum.PROGRAMADOR, "rutaFoto");
	modelo.agregarPersona(dto);
	modelo.guardarPersonaEnJSON();

	File destino = new File(carpetaTemporal.getRoot(), "exportado.json");

	modelo.exportarJson(destino.getAbsolutePath());

	assertEquals(true, destino.exists());
	assertEquals(true, destino.length() > 0);

	}

	@Test
	public void limpiarCacheFotosTest() throws IOException {
	File foto1 = new File(carpetaFotosTemporal, "foto1.jpg");
	File foto2 = new File(carpetaFotosTemporal, "foto2.jpg");

	foto1.createNewFile();
	foto2.createNewFile();

	modelo.limpiarCacheFotos();

	assertEquals(0, carpetaFotosTemporal.listFiles().length);

	}
	
	@Test
	public void obtenerNombrePorIndiceTest() {
		modelo.agregarPersona(new PersonaDto("Leo", "Messi", 5, RolEnum.PROGRAMADOR, "foto"));
		
		String nombreCompleto = modelo.obtenerNombrePorIndice(0);
		assertEquals("Leo Messi", nombreCompleto);
		
		assertEquals("", modelo.obtenerNombrePorIndice(5));
		assertEquals("", modelo.obtenerNombrePorIndice(-1));
	}
	
	//TEST ERRORES
	
	@Test(expected = IllegalArgumentException.class)
	public void personaConNombreVacioTest() {
	PersonaDto dto = new PersonaDto("", "Messi", 5, RolEnum.PROGRAMADOR, "rutaFoto");
	modelo.agregarPersona(dto);
	}

	@Test(expected = IllegalArgumentException.class)
	public void personaConApellidoVacioTest() {
	PersonaDto dto = new PersonaDto("Leo", "", 5, RolEnum.PROGRAMADOR, "rutaFoto");
	modelo.agregarPersona(dto);
	}

	@Test(expected = IllegalArgumentException.class)
	public void personaConPuntuacionInvalidaTest() {
	PersonaDto dto = new PersonaDto("Leo", "Messi", 10, RolEnum.PROGRAMADOR, "rutaFoto");
	modelo.agregarPersona(dto);
	}

	@Test(expected = IllegalArgumentException.class)
	public void personaConRolVacioTest() {
	PersonaDto dto = new PersonaDto("Leo", "Messi", 5, null, "rutaFoto");
	modelo.agregarPersona(dto);	
	}

	@Test(expected = IllegalArgumentException.class)
	public void personaNombreInvalidoTest() {
	PersonaDto dto = new PersonaDto("1234", "Messi", 5, RolEnum.PROGRAMADOR, "rutaFoto");
	modelo.agregarPersona(dto);
	}

	@Test(expected = IllegalArgumentException.class)
	public void personaApellidoInvalidoTest() {
	PersonaDto dto = new PersonaDto("Leo", "1234", 5, RolEnum.PROGRAMADOR, "rutaFoto");
	modelo.agregarPersona(dto);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void agregarPersonaDuplicadaTest() {
		PersonaDto dto1 = new PersonaDto("Leo", "Messi", 5, RolEnum.PROGRAMADOR, "foto");
		PersonaDto dto2 = new PersonaDto("Leo", "Messi", 4, RolEnum.ARQUITECTO, "foto2"); 
		
		modelo.agregarPersona(dto1);
		modelo.agregarPersona(dto2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void editarPersonaGenerandoDuplicadoTest() {
		modelo.agregarPersona(new PersonaDto("Leo", "Messi", 5, RolEnum.PROGRAMADOR, "foto"));
		modelo.agregarPersona(new PersonaDto("Cristiano", "Ronaldo", 4, RolEnum.ARQUITECTO, "foto"));
		
		
		PersonaDto dtoDuplicado = new PersonaDto("Leo", "Messi", 4, RolEnum.ARQUITECTO, "foto");
		modelo.editarPersona(1, dtoDuplicado); 
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void editarPersonaIndiceInvalidoTest() {
		PersonaDto dto = new PersonaDto("Leo", "Messi", 5, RolEnum.PROGRAMADOR, "foto");
		modelo.editarPersona(-1, dto);
	}

	@Test(expected = IllegalArgumentException.class)
	public void archivoInvalidoTest() {
	modelo.cargarDesdeJSON("archivo.txt");
	}

	@Test(expected = IllegalArgumentException.class)
	public void formatoImagenInvalidoTest() throws IOException {
	File archivo = carpetaTemporal.newFile("archivo.txt");
	modelo.guardarFoto(archivo);
	}
	
	
}
