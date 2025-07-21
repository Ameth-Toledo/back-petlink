package org.petlink.service;

import org.petlink.model.SolicitudCedente;
import org.petlink.repository.SolicitudCedenteRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class SolicitudCedenteService {

    private final SolicitudCedenteRepository repository = new SolicitudCedenteRepository();

    public List<SolicitudCedente> getAll() throws Exception {
        return repository.findAll();
    }

    public SolicitudCedente getById(int id) throws Exception {
        return repository.findById(id);
    }

    public void create(SolicitudCedente s) throws Exception {
        validate(s);
        if (s.getFecha_solicitudCedente() == null) {
            s.setFecha_solicitudCedente(Date.valueOf(LocalDate.now()));
        }
        repository.save(s);
    }

    public void update(int id, SolicitudCedente s) throws Exception {
        validate(s);
        repository.update(id, s);
    }

    public void delete(int id) throws Exception {
        repository.delete(id);
    }

    private void validate(SolicitudCedente s) throws Exception {
        if (s.getEstado_solicitudCedente() == null || s.getEstado_solicitudCedente().isEmpty()) {
            throw new Exception("El estado de la solicitud es obligatorio.");
        }

        if (!s.getEstado_solicitudCedente().matches("(?i)(pendiente|aprobado|rechazado)")) {
            throw new Exception("Estado inválido. Solo se permiten: pendiente, aprobado o rechazado.");
        }

        if (s.getFotos_mascotas() == null || s.getFotos_mascotas().size() != 3) {
            throw new Exception("Se deben proporcionar exactamente 3 fotos de la mascota.");
        }

        if (s.getCodigo_usuario() <= 0) {
            throw new Exception("El código de usuario debe ser válido.");
        }
    }
}