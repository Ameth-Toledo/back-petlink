package org.petlink.repository;

import org.petlink.config.DatabaseConfig;
import org.petlink.model.AdopcionRealizada;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdopcionRealizadaRepository {

    public List<AdopcionRealizada> findAll() throws Exception {
        List<AdopcionRealizada> list = new ArrayList<>();
        String sql = "SELECT * FROM adopcion_realizada";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }

        return list;
    }

    public AdopcionRealizada findById(int id) throws Exception {
        String sql = "SELECT * FROM adopcion_realizada WHERE id_adopcionRealizada=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }

    public void save(AdopcionRealizada a) throws Exception {
        String sql = "INSERT INTO adopcion_realizada (fecha_adopcion, codigo_usuario, codigo_mascota) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, a.getFecha_adopcion());
            stmt.setInt(2, a.getCodigo_usuario());
            stmt.setInt(3, a.getCodigo_mascota());

            stmt.executeUpdate();
        }
    }

    public void update(int id, AdopcionRealizada a) throws Exception {
        String sql = "UPDATE adopcion_realizada SET fecha_adopcion=?, codigo_usuario=?, codigo_mascota=? WHERE id_adopcionRealizada=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, a.getFecha_adopcion());
            stmt.setInt(2, a.getCodigo_usuario());
            stmt.setInt(3, a.getCodigo_mascota());
            stmt.setInt(4, id);

            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM adopcion_realizada WHERE id_adopcionRealizada=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private AdopcionRealizada map(ResultSet rs) throws SQLException {
        AdopcionRealizada a = new AdopcionRealizada();
        a.setId_adopcionRealizada(rs.getInt("id_adopcionRealizada"));
        a.setFecha_adopcion(rs.getDate("fecha_adopcion"));
        a.setCodigo_usuario(rs.getInt("codigo_usuario"));
        a.setCodigo_mascota(rs.getInt("codigo_mascota"));
        return a;
    }
}
