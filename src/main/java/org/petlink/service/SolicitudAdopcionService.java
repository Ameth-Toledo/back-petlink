package org.petlink.service;

import java.util.List;

import org.petlink.model.SolicitudAdopcion;
import org.petlink.repository.SolicitudAdopcionRepository;

public class SolicitudAdopcionService {

    private final SolicitudAdopcionRepository repo = new SolicitudAdopcionRepository();

    public List<SolicitudAdopcion> getAll() throws Exception {
        return repo.findAll();
    }

    public SolicitudAdopcion getById(int id) throws Exception {
        return repo.findById(id);
    }

    public int createAndReturnId(SolicitudAdopcion s) throws Exception {
        validate(s);
        return repo.saveAndReturnId(s); 
    }

    public void create(SolicitudAdopcion s) throws Exception {
        validate(s);
        repo.save(s);
    }

    public void update(int id, SolicitudAdopcion s) throws Exception {
        SolicitudAdopcion actual = repo.findById(id);
        if (actual == null) {
            throw new Exception("Solicitud no encontrada");
        }

        validate(s);

        repo.update(id, s);
    }

    public void delete(int id) throws Exception {
        repo.delete(id);
    }

    private void validate(SolicitudAdopcion s) throws Exception {
        if (s.getOcupacion_usuario() == null || s.getOcupacion_usuario().isEmpty()) {
            throw new Exception("La ocupación es obligatoria.");
        }
        if (s.getTipo_vivienda() == null || s.getTipo_vivienda().isEmpty()) {
            throw new Exception("El tipo de vivienda es obligatorio.");
        }
        if (s.getCodigo_usuario() <= 0) {
            throw new Exception("El ID del usuario adoptante no es válido.");
        }
    }
}
