package org.petlink.repository;

import org.petlink.config.DatabaseConfig;
import org.petlink.model.Vacuna;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VacunaRepository {

    public List<Vacuna> findAll() throws Exception {
        List<Vacuna> vacunas = new ArrayList<>();
        String sql = "SELECT * FROM vacuna";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                vacunas.add(map(rs));
            }
        }

        return vacunas;
    }

    public Vacuna findById(int id) throws Exception {
        String sql = "SELECT * FROM vacuna WHERE id_vacuna=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }

    public void save(Vacuna v) throws Exception {
        String sql = "INSERT INTO vacuna (nombreVacuna, descripcion) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, v.getNombreVacuna());
            stmt.setString(2, v.getDescripcion());

            stmt.executeUpdate();
        }
    }

    public void update(int id, Vacuna v) throws Exception {
        String sql = "UPDATE vacuna SET nombreVacuna=?, descripcion=? WHERE id_vacuna=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, v.getNombreVacuna());
            stmt.setString(2, v.getDescripcion());
            stmt.setInt(3, id);

            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM vacuna WHERE id_vacuna=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Vacuna map(ResultSet rs) throws SQLException {
        Vacuna v = new Vacuna();
        v.setId_vacuna(rs.getInt("id_vacuna"));
        v.setNombreVacuna(rs.getString("nombreVacuna"));
        v.setDescripcion(rs.getString("descripcion"));
        return v;
    }
}
