package org.petlink.service;

import org.petlink.model.Vacuna;
import org.petlink.repository.VacunaRepository;

import java.util.List;

public class VacunaService {

    private final VacunaRepository repo = new VacunaRepository();

    public List<Vacuna> getAll() throws Exception {
        return repo.findAll();
    }

    public Vacuna getById(int id) throws Exception {
        return repo.findById(id);
    }

    public void create(Vacuna v) throws Exception {
        validate(v);
        repo.save(v);
    }

    public void update(int id, Vacuna v) throws Exception {
        validate(v);
        repo.update(id, v);
    }

    public void delete(int id) throws Exception {
        repo.delete(id);
    }

    private void validate(Vacuna v) throws Exception {
        if (v.getNombreVacuna() == null || v.getNombreVacuna().isEmpty()) {
            throw new Exception("El nombre de la vacuna es obligatorio.");
        }
        if (v.getDescripcion() == null || v.getDescripcion().isEmpty()) {
            throw new Exception("La descripción es obligatoria.");
        }
    }
}
