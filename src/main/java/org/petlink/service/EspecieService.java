package org.petlink.service;

import org.petlink.model.Especie;
import org.petlink.repository.EspecieRepository;

import java.util.List;

public class EspecieService {

    private final EspecieRepository repo = new EspecieRepository();

    public List<Especie> getAll() throws Exception {
        return repo.findAll();
    }

    public Especie getById(int id) throws Exception {
        return repo.findById(id);
    }

    public void create(Especie e) throws Exception {
        validate(e);
        repo.save(e);
    }

    public void update(int id, Especie e) throws Exception {
        validate(e);
        repo.update(id, e);
    }

    public void delete(int id) throws Exception {
        repo.delete(id);
    }

    private void validate(Especie e) throws Exception {
        if (e.getNombre() == null || e.getNombre().isEmpty()) {
            throw new Exception("El nombre es obligatorio.");
        }
    }
}
