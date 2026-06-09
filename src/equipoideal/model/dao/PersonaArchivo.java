package equipoideal.model.dao;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import equipoideal.model.Persona;
import equipoideal.model.dto.PersonaDto;
import equipoideal.util.PersonaValidator;

public class PersonaArchivo {

	private static void generarJsonPersona(String archivo, ArrayList<Persona> personas) {
		if (archivo == null || archivo.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre del archivo no puede estar vacío.");
		}

		if (personas == null) {
			throw new IllegalArgumentException("La lista de personas no puede ser null.");
		}
		
		
		  Gson gson = new GsonBuilder().setPrettyPrinting().create();

			
		    String json =gson.toJson(personas);

		        try (FileWriter writer = new FileWriter(archivo)) {
		        	writer.write(json);
		        } catch (IOException e) {
		        	throw new RuntimeException("Error al escribir el archivo JSON: " + e.getMessage());
		        }
		    
	}

	public static ArrayList<Persona> cargarJSON(String archivo) {

		if (archivo == null || archivo.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre del archivo no puede estar vacío.");
		}
		
		File file = new File(archivo);
		if (!file.exists()) {
		    throw new IllegalArgumentException("El archivo no existe.");
		}
		
		if (file.length() == 0) {
			throw new IllegalArgumentException("El archivo está totalmente vacío.");
		}

		Gson gson = new Gson();

		try (Reader reader = new FileReader(archivo)) {

			Type personaType = new TypeToken<ArrayList<Persona>>() {}.getType();

			ArrayList<Persona> personas = gson.fromJson(reader, personaType);

			for (Persona p : personas) {
				PersonaDto dto = p.toDto();
				PersonaValidator.validarPersona(dto);
			}

			return personas;

		} catch (JsonSyntaxException e) {

			
			throw new IllegalArgumentException("El JSON tiene errores de sintaxis y no se pudo procesar.");

		} catch (IOException e) {
			
			throw new RuntimeException("Error físico de lectura/escritura al abrir el archivo: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			
			throw e;
		}
	}

	// TODO con los cambios propuestos, quizas este sería el único metodo publico y
	// los otros podrian ser privados, ya que el cliente solo podría exportarse un
	// JSON

	public static void exportarArchivoJSON(String destino, ArrayList<Persona> personas) {
		generarJsonPersona(destino, personas);
	}
	
	

		

}
