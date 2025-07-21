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

    public List<SolicitudAdopcion> findAll() throws Exception {
        List<SolicitudAdopcion> list = new ArrayList<>();
        String sql = "SELECT * FROM solicitud_adopcion";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public SolicitudAdopcion findById(int id) throws Exception {
        String sql = "SELECT * FROM solicitud_adopcion WHERE id_solicitudAdopcion=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }

    public void save(SolicitudAdopcion s) throws Exception {
        String sql = "INSERT INTO solicitud_adopcion (ocupacion_usuario, tipo_vivienda, mascotas_previas, estado_vivienda, permisopara_mascotas, numero_personas, niños_casa, experiencia_mascotas, fecha_solicitudAdopcion, foto_vivienda, estado_solicitudAdopcion, codigo_usuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getOcupacion_usuario());
            stmt.setString(2, s.getTipo_vivienda());
            stmt.setString(3, s.getMascotas_previas());
            stmt.setString(4, s.getEstado_vivienda());
            stmt.setString(5, s.getPermisopara_mascotas());
            stmt.setInt(6, s.getNumero_personas());
            stmt.setString(7, s.getNiños_casa());
            stmt.setString(8, s.getExperiencia_mascotas());
            stmt.setDate(9, s.getFecha_solicitudAdopcion());
            stmt.setString(10, s.getFoto_vivienda());
            stmt.setString(11, s.getEstado_solicitudAdopcion());
            stmt.setInt(12, s.getCodigo_usuario());

            stmt.executeUpdate();
        }
    }

    public int saveAndReturnId(SolicitudAdopcion s) throws Exception {
        String sql = "INSERT INTO solicitud_adopcion (" +
                    "ocupacion_usuario, tipo_vivienda, mascotas_previas, " +
                    "estado_vivienda, permisopara_mascotas, numero_personas, " +
                    "niños_casa, experiencia_mascotas, fecha_solicitudAdopcion, " +
                    "foto_vivienda, estado_solicitudAdopcion, codigo_usuario" +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, s.getOcupacion_usuario());
            stmt.setString(2, s.getTipo_vivienda());
            stmt.setString(3, s.getMascotas_previas());
            stmt.setString(4, s.getEstado_vivienda());
            stmt.setString(5, s.getPermisopara_mascotas());
            stmt.setInt(6, s.getNumero_personas());
            stmt.setString(7, s.getNiños_casa());
            stmt.setString(8, s.getExperiencia_mascotas());
            stmt.setDate(9, s.getFecha_solicitudAdopcion());
            stmt.setString(10, s.getFoto_vivienda());
            stmt.setString(11, s.getEstado_solicitudAdopcion());
            stmt.setInt(12, s.getCodigo_usuario());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La creación de la solicitud falló, no se afectaron filas.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("La creación de la solicitud falló, no se obtuvo ID.");
                }
            }
        }
    }

    public void update(int id, SolicitudAdopcion s) throws Exception {
        String sql = "UPDATE solicitud_adopcion SET ocupacion_usuario=?, tipo_vivienda=?, mascotas_previas=?, estado_vivienda=?, permisopara_mascotas=?, numero_personas=?, niños_casa=?, experiencia_mascotas=?, fecha_solicitudAdopcion=?, foto_vivienda=?, estado_solicitudAdopcion=?, codigo_usuario=? WHERE id_solicitudAdopcion=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getOcupacion_usuario());
            stmt.setString(2, s.getTipo_vivienda());
            stmt.setString(3, s.getMascotas_previas());
            stmt.setString(4, s.getEstado_vivienda());
            stmt.setString(5, s.getPermisopara_mascotas());
            stmt.setInt(6, s.getNumero_personas());
            stmt.setString(7, s.getNiños_casa());
            stmt.setString(8, s.getExperiencia_mascotas());
            stmt.setDate(9, s.getFecha_solicitudAdopcion());
            stmt.setString(10, s.getFoto_vivienda());
            stmt.setString(11, s.getEstado_solicitudAdopcion());
            stmt.setInt(12, s.getCodigo_usuario());
            stmt.setInt(13, id);

            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM solicitud_adopcion WHERE id_solicitudAdopcion=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private SolicitudAdopcion map(ResultSet rs) throws SQLException {
        SolicitudAdopcion s = new SolicitudAdopcion();
            s.setId_solicitudAdopcion(rs.getInt("id_solicitudAdopcion"));
            s.setOcupacion_usuario(rs.getString("ocupacion_usuario"));
            s.setTipo_vivienda(rs.getString("tipo_vivienda"));
            s.setMascotas_previas(rs.getString("mascotas_previas"));
            s.setEstado_vivienda(rs.getString("estado_vivienda"));
            s.setPermisopara_mascotas(rs.getString("permisopara_mascotas"));
            s.setNumero_personas(rs.getInt("numero_personas"));
            s.setNiños_casa(rs.getString("niños_casa"));
            s.setExperiencia_mascotas(rs.getString("experiencia_mascotas"));
            s.setFecha_solicitudAdopcion(rs.getDate("fecha_solicitudAdopcion"));
            s.setFoto_vivienda(rs.getString("foto_vivienda"));  
            s.setEstado_solicitudAdopcion(rs.getString("estado_solicitudAdopcion"));
            s.setCodigo_usuario(rs.getInt("codigo_usuario"));
        return s;
    }
}
