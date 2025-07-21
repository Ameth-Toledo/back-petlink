package org.petlink.service;

import org.petlink.model.AdopcionRealizada;
import org.petlink.repository.AdopcionRealizadaRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class AdopcionRealizadaService {

    private final AdopcionRealizadaRepository repo = new AdopcionRealizadaRepository();

    public List<AdopcionRealizada> getAll() throws Exception {
        return repo.findAll();
    }

    public AdopcionRealizada getById(int id) throws Exception {
        return repo.findById(id);
    }

    public void create(AdopcionRealizada a) throws Exception {
        if (a.getFecha_adopcion() == null) {
            a.setFecha_adopcion(Date.valueOf(LocalDate.now()));
        }
        validate(a);
        repo.save(a);
    }

    public void update(int id, AdopcionRealizada a) throws Exception {
        validate(a);
        repo.update(id, a);
    }

    public void delete(int id) throws Exception {
        repo.delete(id);
    }

    private void validate(AdopcionRealizada a) throws Exception {
        if (a.getCodigo_usuario() <= 0 || a.getCodigo_mascota() <= 0) {
            throw new Exception("Los IDs de usuario y mascota deben ser válidos.");
        }
    }
}
