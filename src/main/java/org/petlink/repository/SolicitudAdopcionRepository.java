package org.petlink.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.petlink.config.DatabaseConfig;
import org.petlink.model.SolicitudAdopcion;

public class SolicitudAdopcionRepository {

    public List<SolicitudAdopcion> findAll() throws SQLException {
        List<SolicitudAdopcion> list = new ArrayList<>();
        String sql = "SELECT * FROM solicitud_adopcion";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (Exception e) {
            throw new SQLException("Error al obtener todas las solicitudes de adopción", e);
        }
        return list;
    }

    public SolicitudAdopcion findById(int id) throws SQLException {
        String sql = "SELECT * FROM solicitud_adopcion WHERE id_solicitud=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (Exception e) {
            throw new SQLException("Error al obtener la solicitud de adopción con ID: " + id, e);
        }
    }

    public void save(SolicitudAdopcion s) throws SQLException {
        String sql = "INSERT INTO solicitud_adopcion (" +
            "mascota_id, adoptante_id, nombre_solicitante, apellido_paterno, apellido_materno, " +
            "edad, correo, ocupacion, personas_vivienda, hay_ninos, permite_mascotas, " +
            "tipo_vivienda, tipo_propiedad, experiencia, historial_mascotas, " +
            "ine_document, espacio_mascota, fecha_solicitud, estado_solicitud" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setStatementParameters(stmt, s);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new SQLException("Error al guardar la solicitud de adopción", e);
        }
    }

    public int saveAndReturnId(SolicitudAdopcion s) throws SQLException {
        String sql = "INSERT INTO solicitud_adopcion (" +
            "mascota_id, adoptante_id, nombre_solicitante, apellido_paterno, apellido_materno, " +
            "edad, correo, ocupacion, personas_vivienda, hay_ninos, permite_mascotas, " +
            "tipo_vivienda, tipo_propiedad, experiencia, historial_mascotas, " +
            "ine_document, espacio_mascota, fecha_solicitud, estado_solicitud" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setStatementParameters(stmt, s);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID generado");
                }
            }
        } catch (Exception e) {
            throw new SQLException("Error al guardar la solicitud de adopción y obtener ID", e);
        }
    }

    public void update(int id, SolicitudAdopcion s) throws SQLException {
        String sql = "UPDATE solicitud_adopcion SET " +
            "mascota_id=?, adoptante_id=?, nombre_solicitante=?, apellido_paterno=?, apellido_materno=?, " +
            "edad=?, correo=?, ocupacion=?, personas_vivienda=?, hay_ninos=?, permite_mascotas=?, " +
            "tipo_vivienda=?, tipo_propiedad=?, experiencia=?, historial_mascotas=?, " +
            "ine_document=?, espacio_mascota=?, fecha_solicitud=?, estado_solicitud=? " +
            "WHERE id_solicitud=?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setStatementParameters(stmt, s);
            stmt.setInt(20, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new SQLException("Error al actualizar la solicitud de adopción con ID: " + id, e);
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM solicitud_adopcion WHERE id_solicitud=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new SQLException("Error al eliminar la solicitud de adopción con ID: " + id, e);
        }
    }

    private void setStatementParameters(PreparedStatement stmt, SolicitudAdopcion s) throws SQLException {
        stmt.setString(1, s.getMascotaId());
        stmt.setString(2, s.getAdoptanteId());
        stmt.setString(3, s.getNombre());
        stmt.setString(4, s.getApellidoPaterno());
        stmt.setString(5, s.getApellidoMaterno());
        stmt.setInt(6, s.getEdad());
        stmt.setString(7, s.getCorreo());
        stmt.setString(8, s.getOcupacion());
        stmt.setInt(9, s.getPersonasVivienda());
        stmt.setString(10, s.getHayNinos());
        stmt.setString(11, s.getPermiteMascotas());
        stmt.setString(12, s.getTipoVivienda());
        stmt.setString(13, s.getTipoPropiedad());
        stmt.setString(14, s.getExperiencia());
        stmt.setString(15, s.getHistorialMascotas());
        stmt.setString(16, s.getIneDocument());
        stmt.setString(17, s.getEspacioMascota());
        stmt.setDate(18, s.getFechaSolicitud());
        stmt.setString(19, s.getEstadoSolicitud());
    }

    private SolicitudAdopcion map(ResultSet rs) throws SQLException {
        SolicitudAdopcion s = new SolicitudAdopcion();
        s.setMascotaId(rs.getString("mascota_id"));
        s.setAdoptanteId(rs.getString("adoptante_id"));
        s.setNombre(rs.getString("nombre_solicitante"));
        s.setApellidoPaterno(rs.getString("apellido_paterno"));
        s.setApellidoMaterno(rs.getString("apellido_materno"));
        s.setEdad(rs.getInt("edad"));
        s.setCorreo(rs.getString("correo"));
        s.setOcupacion(rs.getString("ocupacion"));
        s.setPersonasVivienda(rs.getInt("personas_vivienda"));
        s.setHayNinos(rs.getString("hay_ninos"));
        s.setPermiteMascotas(rs.getString("permite_mascotas"));
        s.setTipoVivienda(rs.getString("tipo_vivienda"));
        s.setTipoPropiedad(rs.getString("tipo_propiedad"));
        s.setExperiencia(rs.getString("experiencia"));
        s.setHistorialMascotas(rs.getString("historial_mascotas"));
        s.setIneDocument(rs.getString("ine_document"));
        s.setEspacioMascota(rs.getString("espacio_mascota"));
        s.setFechaSolicitud(rs.getDate("fecha_solicitud"));
        s.setEstadoSolicitud(rs.getString("estado_solicitud"));
        return s;
    }
}