package org.petlink.repository;

import org.petlink.config.DatabaseConfig;
import org.petlink.model.Especie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecieRepository {

    public List<Especie> findAll() throws Exception {
        List<Especie> list = new ArrayList<>();
        String sql = "SELECT * FROM especie";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }

        return list;
    }

    public Especie findById(int id) throws Exception {
        String sql = "SELECT * FROM especie WHERE id_especie=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }

    public void save(Especie e) throws Exception {
        String sql = "INSERT INTO especie (nombre) VALUES (?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, e.getNombre());
            stmt.executeUpdate();
        }
    }

    public void update(int id, Especie e) throws Exception {
        String sql = "UPDATE especie SET nombre=? WHERE id_especie=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, e.getNombre());
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM especie WHERE id_especie=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Especie map(ResultSet rs) throws SQLException {
        Especie e = new Especie();
        e.setId_especie(rs.getInt("id_especie"));
        e.setNombre(rs.getString("nombre"));
        return e;
    }
}
