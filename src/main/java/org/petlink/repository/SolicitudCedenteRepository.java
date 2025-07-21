package org.petlink.repository;

import org.petlink.config.DatabaseConfig;
import org.petlink.model.SolicitudCedente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitudCedenteRepository {

    public List<SolicitudCedente> findAll() throws Exception {
        List<SolicitudCedente> lista = new ArrayList<>();
        String sql = "SELECT * FROM solicitud_cedente";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResult(rs));
            }
        }

        return lista;
    }

    public SolicitudCedente findById(int id) throws Exception {
        String sql = "SELECT * FROM solicitud_cedente WHERE id_solicitudCedente = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResult(rs);
            }
        }

        return null;
    }

    public void save(SolicitudCedente s) throws Exception {
        String sql = "INSERT INTO solicitud_cedente (estado_solicitudCedente, fecha_solicitudCedente, fotos_mascotas, codigo_usuario) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, s.getEstado_solicitudCedente());
            stmt.setDate(2, s.getFecha_solicitudCedente());
            stmt.setString(3, s.getFotos_mascotasAsJson());
            stmt.setInt(4, s.getCodigo_usuario());

            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    s.setId_solicitudCedente(rs.getInt(1));
                }
            }
        }
    }

    public void update(int id, SolicitudCedente s) throws Exception {
        String sql = "UPDATE solicitud_cedente SET estado_solicitudCedente=?, fecha_solicitudCedente=?, fotos_mascotas=?, codigo_usuario=? WHERE id_solicitudCedente=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getEstado_solicitudCedente());
            stmt.setDate(2, s.getFecha_solicitudCedente());
            stmt.setString(3, s.getFotos_mascotasAsJson());
            stmt.setInt(4, s.getCodigo_usuario());
            stmt.setInt(5, id);

            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM solicitud_cedente WHERE id_solicitudCedente = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private SolicitudCedente mapResult(ResultSet rs) throws SQLException {
        SolicitudCedente s = new SolicitudCedente();
        s.setId_solicitudCedente(rs.getInt("id_solicitudCedente"));
        s.setEstado_solicitudCedente(rs.getString("estado_solicitudCedente"));
        s.setFecha_solicitudCedente(rs.getDate("fecha_solicitudCedente"));
        s.setFotos_mascotasFromJson(rs.getString("fotos_mascotas"));
        s.setCodigo_usuario(rs.getInt("codigo_usuario"));
        return s;
    }
}