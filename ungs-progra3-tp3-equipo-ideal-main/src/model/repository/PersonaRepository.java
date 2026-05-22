package model.repository;

import java.util.ArrayList;

import equipoideal.model.Persona;

public interface PersonaRepository {
	ArrayList<Persona> loadAll();
    void saveAll(ArrayList<Persona> personas);
    void cleanAll();
}
