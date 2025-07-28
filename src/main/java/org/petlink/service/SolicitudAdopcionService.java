package org.petlink.service;

import java.sql.SQLException;
import java.util.List;

import org.petlink.model.SolicitudAdopcion;
import org.petlink.repository.SolicitudAdopcionRepository;

public class SolicitudAdopcionService {

    private final SolicitudAdopcionRepository repo = new SolicitudAdopcionRepository();

    public List<SolicitudAdopcion> getAll() throws SQLException {
        return repo.findAll();
    }

    public SolicitudAdopcion getById(int id) throws SQLException {
        return repo.findById(id);
    }

    public int createAndReturnId(SolicitudAdopcion s) throws Exception {
        validate(s);
        try {
            return repo.saveAndReturnId(s);
        } catch (SQLException e) {
            throw new Exception("Error al guardar la solicitud: " + e.getMessage(), e);
        }
    }

    public void create(SolicitudAdopcion s) throws Exception {
        validate(s);
        try {
            repo.save(s);
        } catch (SQLException e) {
            throw new Exception("Error al guardar la solicitud: " + e.getMessage(), e);
        }
    }

    public void update(int id, SolicitudAdopcion s) throws Exception {
        try {
            if (repo.findById(id) == null) {
                throw new Exception("Solicitud no encontrada");
            }
            validate(s);
            repo.update(id, s);
        } catch (SQLException e) {
            throw new Exception("Error al actualizar la solicitud: " + e.getMessage(), e);
        }
    }

    public void delete(int id) throws Exception {
        try {
            repo.delete(id);
        } catch (SQLException e) {
            throw new Exception("Error al eliminar la solicitud: " + e.getMessage(), e);
        }
    }

    private void validate(SolicitudAdopcion s) throws Exception {
        if (s.getMascotaId() == null || s.getMascotaId().isEmpty()) {
            throw new Exception("El ID de la mascota es obligatorio");
        }
        if (s.getAdoptanteId() == null || s.getAdoptanteId().isEmpty()) {
            throw new Exception("El ID del adoptante es obligatorio");
        }
        if (s.getNombre() == null || s.getNombre().isEmpty()) {
            throw new Exception("El nombre del solicitante es obligatorio");
        }
        if (s.getCorreo() == null || s.getCorreo().isEmpty()) {
            throw new Exception("El correo electrónico es obligatorio");
        }
        if (s.getIneDocument() == null || s.getIneDocument().isEmpty()) {
            throw new Exception("El documento de identificación es obligatorio");
        }
        if (s.getEspacioMascota() == null || s.getEspacioMascota().isEmpty()) {
            throw new Exception("La foto del espacio para la mascota es obligatoria");
        }
    }
}