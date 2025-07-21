package org.petlink.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

import org.petlink.config.DatabaseConfig;
import org.petlink.model.Publicacion;
import org.petlink.repository.PublicacionRepository;

public class PublicacionService {

    private final PublicacionRepository repository = new PublicacionRepository();

    public List<Publicacion> getAll() throws Exception {
        return repository.findAll();
    }

    public Publicacion getById(int id) throws Exception {
        return repository.findById(id);
    }

    public void create(Publicacion p) throws Exception {
        if (p.getFecha_publicacion() == null) {
            p.setFecha_publicacion(Date.valueOf(LocalDate.now()));
        }
        validate(p);
        repository.save(p);
    }

    public void update(int id, Publicacion p) throws Exception {
        validate(p);
        repository.update(id, p);
    }

    public void delete(int id) throws Exception {
        repository.delete(id);
    }

    public void vincularSolicitudConPublicacion(int idSolicitudAdopcion, int idPublicacion) throws Exception {
    String sql = "INSERT INTO solicitudadopcion_publicacion (id_solicitudAdopcion, id_publicacion) VALUES (?, ?)";

    try (Connection conn = DatabaseConfig.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idSolicitudAdopcion);
        stmt.setInt(2, idPublicacion);
        stmt.executeUpdate();

    } catch (Exception e) {
        throw new Exception("Error al vincular solicitud con publicación: " + e.getMessage());
    }
    }

    private void validate(Publicacion p) throws Exception {
        if (p.getEstado_publicacion() == null || p.getEstado_publicacion().isEmpty())
            throw new Exception("El estado es obligatorio.");
        if (p.getCodigo() <= 0 || p.getCodigo_solicitudCedente() <= 0 || p.getCodigo_usuario() <= 0)
            throw new Exception("IDs foráneos inválidos.");
    }
}
