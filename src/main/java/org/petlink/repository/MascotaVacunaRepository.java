package org.petlink.repository;

import org.petlink.config.DatabaseConfig;
import org.petlink.model.MascotaVacuna;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MascotaVacunaRepository {

    public List<MascotaVacuna> findAll() throws Exception {
        List<MascotaVacuna> list = new ArrayList<>();
        String sql = "SELECT * FROM mascota_vacuna";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }
        }

        return list;
    }

    public MascotaVacuna findById(int id) throws Exception {
        String sql = "SELECT * FROM mascota_vacuna WHERE id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }

    public void save(MascotaVacuna mv) throws Exception {
        String sql = "INSERT INTO mascota_vacuna (codigo_mascota, codigo_vacuna, fecha_aplicacion) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mv.getCodigo_mascota());
            stmt.setInt(2, mv.getCodigo_vacuna());
            stmt.setDate(3, mv.getFecha_aplicacion());

            stmt.executeUpdate();
        }
    }

    public void update(int id, MascotaVacuna mv) throws Exception {
        String sql = "UPDATE mascota_vacuna SET codigo_mascota=?, codigo_vacuna=?, fecha_aplicacion=? WHERE id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mv.getCodigo_mascota());
            stmt.setInt(2, mv.getCodigo_vacuna());
            stmt.setDate(3, mv.getFecha_aplicacion());
            stmt.setInt(4, id);

            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM mascota_vacuna WHERE id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private MascotaVacuna map(ResultSet rs) throws SQLException {
        MascotaVacuna mv = new MascotaVacuna();
        mv.setId(rs.getInt("id"));
        mv.setCodigo_mascota(rs.getInt("codigo_mascota"));
        mv.setCodigo_vacuna(rs.getInt("codigo_vacuna"));
        mv.setFecha_aplicacion(rs.getDate("fecha_aplicacion"));
        return mv;
    }
}
