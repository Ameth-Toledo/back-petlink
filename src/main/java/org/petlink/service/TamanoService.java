package org.petlink.service;

import org.petlink.model.Tamano;
import org.petlink.repository.TamanoRepository;

import java.util.List;

public class TamanoService {

    private final TamanoRepository repo = new TamanoRepository();

    public List<Tamano> getAll() throws Exception {
        return repo.findAll();
    }

    public Tamano getById(int id) throws Exception {
        return repo.findById(id);
    }

    public void create(Tamano t) throws Exception {
        validate(t);
        repo.save(t);
    }

    public void update(int id, Tamano t) throws Exception {
        validate(t);
        repo.update(id, t);
    }

    public void delete(int id) throws Exception {
        repo.delete(id);
    }

    private void validate(Tamano t) throws Exception {
        if (t.getDescripcion() == null || t.getDescripcion().isEmpty()) {
            throw new Exception("La descripción es obligatoria.");
        }
    }
}
