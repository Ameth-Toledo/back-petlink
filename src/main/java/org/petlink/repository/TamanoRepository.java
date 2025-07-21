package org.petlink.repository;

import org.petlink.config.DatabaseConfig;
import org.petlink.model.Tamano;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TamanoRepository {

    public List<Tamano> findAll() throws Exception {
        List<Tamano> list = new ArrayList<>();
        String sql = "SELECT * FROM tamano";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }
        }

        return list;
    }

    public Tamano findById(int id) throws Exception {
        String sql = "SELECT * FROM tamano WHERE id_tamano=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }

    public void save(Tamano t) throws Exception {
        String sql = "INSERT INTO tamano (descripcion) VALUES (?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, t.getDescripcion());
            stmt.executeUpdate();
        }
    }

    public void update(int id, Tamano t) throws Exception {
        String sql = "UPDATE tamano SET descripcion=? WHERE id_tamano=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, t.getDescripcion());
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM tamano WHERE id_tamano=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Tamano map(ResultSet rs) throws SQLException {
        Tamano t = new Tamano();
        t.setId_tamano(rs.getInt("id_tamano"));
        t.setDescripcion(rs.getString("descripcion"));
        return t;
    }
}
