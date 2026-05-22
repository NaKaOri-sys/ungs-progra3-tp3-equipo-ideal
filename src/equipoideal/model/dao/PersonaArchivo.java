package equipoideal.model.dao;

import java.io.FileWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import equipoideal.model.Persona;

public class PersonaArchivo {

	public static void generarJsonPersona(String archivo, ArrayList<Persona> personas) {
		  Gson gson = new GsonBuilder().setPrettyPrinting().create();

			
		    String json =gson.toJson(personas);

			
		    try (FileWriter writer =new FileWriter(archivo)) {
		    	writer.write(json);
		    } catch (Exception e) {
		        System.err.println( "Error al generar JSON: " + e.getMessage());

		        throw new RuntimeException( e.getMessage() );
		    }
	}

		

}
