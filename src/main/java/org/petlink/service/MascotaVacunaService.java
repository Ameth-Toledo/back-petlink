package org.petlink.service;

import org.petlink.model.MascotaVacuna;
import org.petlink.repository.MascotaVacunaRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class MascotaVacunaService {

    private final MascotaVacunaRepository repo = new MascotaVacunaRepository();

    public List<MascotaVacuna> getAll() throws Exception {
        return repo.findAll();
    }

    public MascotaVacuna getById(int id) throws Exception {
        return repo.findById(id);
    }

    public void create(MascotaVacuna mv) throws Exception {
        if (mv.getFecha_aplicacion() == null) {
            mv.setFecha_aplicacion(Date.valueOf(LocalDate.now()));
        }
        validate(mv);
        repo.save(mv);
    }

    public void update(int id, MascotaVacuna mv) throws Exception {
        validate(mv);
        repo.update(id, mv);
    }

    public void delete(int id) throws Exception {
        repo.delete(id);
    }

    private void validate(MascotaVacuna mv) throws Exception {
        if (mv.getCodigo_mascota() <= 0 || mv.getCodigo_vacuna() <= 0) {
            throw new Exception("IDs de mascota o vacuna inválidos.");
        }
    }
}
