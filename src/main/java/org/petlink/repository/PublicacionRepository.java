package org.petlink.repository;

import org.petlink.config.DatabaseConfig;
import org.petlink.model.Publicacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublicacionRepository {

    public List<Publicacion> findAll() throws Exception {
        List<Publicacion> list = new ArrayList<>();
        String sql = "SELECT * FROM publicacion";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }

        return list;
    }

    public Publicacion findById(int id) throws Exception {
        String sql = "SELECT * FROM publicacion WHERE id_publicacion=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }

    public void save(Publicacion p) throws Exception {
        String sql = "INSERT INTO publicacion (estado_publicacion, fecha_publicacion, Codigo, codigo_solicitudCedente, codigo_usuario) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getEstado_publicacion());
            stmt.setDate(2, p.getFecha_publicacion());
            stmt.setInt(3, p.getCodigo());
            stmt.setInt(4, p.getCodigo_solicitudCedente());
            stmt.setInt(5, p.getCodigo_usuario());

            stmt.executeUpdate();
        }
    }

    public void update(int id, Publicacion p) throws Exception {
        String sql = "UPDATE publicacion SET estado_publicacion=?, fecha_publicacion=?, Codigo=?, codigo_solicitudCedente=?, codigo_usuario=? WHERE id_publicacion=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getEstado_publicacion());
            stmt.setDate(2, p.getFecha_publicacion());
            stmt.setInt(3, p.getCodigo());
            stmt.setInt(4, p.getCodigo_solicitudCedente());
            stmt.setInt(5, p.getCodigo_usuario());
            stmt.setInt(6, id);

            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM publicacion WHERE id_publicacion=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Publicacion map(ResultSet rs) throws SQLException {
        Publicacion p = new Publicacion();
        p.setId_publicacion(rs.getInt("id_publicacion"));
        p.setEstado_publicacion(rs.getString("estado_publicacion"));
        p.setFecha_publicacion(rs.getDate("fecha_publicacion"));
        p.setCodigo(rs.getInt("Codigo"));
        p.setCodigo_solicitudCedente(rs.getInt("codigo_solicitudCedente"));
        p.setCodigo_usuario(rs.getInt("codigo_usuario"));
        return p;
    }
}
