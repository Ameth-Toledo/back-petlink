package org.petlink.repository;

import org.petlink.config.DatabaseConfig;
import org.petlink.model.Mascota;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MascotaRepository {

    public List<Mascota> findAll() throws Exception {
        List<Mascota> mascotas = new ArrayList<>();
        String query = "SELECT * FROM mascota";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                mascotas.add(mapResult(rs));
            }
        }

        return mascotas;
    }

    public Mascota findById(int id) throws Exception {
        String query = "SELECT * FROM mascota WHERE id_mascotas = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResult(rs);
            }
        }

        return null;
    }

    public void save(Mascota m) throws Exception {
        String query = "INSERT INTO mascota (nombre_mascotas, codigo_especie, sexo, peso, codigo_tamaño, raza, esterilizado, desparasitado, discapacitado, enfermedades, codigo_vacunas, descripcion, id_Cedente) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, m.getNombre_mascotas());
            stmt.setInt(2, m.getCodigo_especie());
            stmt.setString(3, m.getSexo());
            stmt.setFloat(4, m.getPeso());
            stmt.setInt(5, m.getCodigo_tamaño());
            stmt.setString(6, m.getRaza());
            stmt.setString(7, m.getEsterilizado());
            stmt.setString(8, m.getDesparasitado());
            stmt.setString(9, m.getDiscapacitado());
            stmt.setString(10, m.getEnfermedades());
            stmt.setInt(11, m.getCodigo_vacunas());
            stmt.setString(12, m.getDescripcion());
            stmt.setInt(13, m.getId_Cedente());
            stmt.executeUpdate();
        }
    }

    public void update(int id, Mascota m) throws Exception {
        String query = "UPDATE mascota SET nombre_mascotas=?, codigo_especie=?, sexo=?, peso=?, codigo_tamaño=?, raza=?, esterilizado=?, desparasitado=?, discapacitado=?, enfermedades=?, codigo_vacunas=?, descripcion=?, id_Cedente=? WHERE id_mascotas=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, m.getNombre_mascotas());
            stmt.setInt(2, m.getCodigo_especie());
            stmt.setString(3, m.getSexo());
            stmt.setFloat(4, m.getPeso());
            stmt.setInt(5, m.getCodigo_tamaño());
            stmt.setString(6, m.getRaza());
            stmt.setString(7, m.getEsterilizado());
            stmt.setString(8, m.getDesparasitado());
            stmt.setString(9, m.getDiscapacitado());
            stmt.setString(10, m.getEnfermedades());
            stmt.setInt(11, m.getCodigo_vacunas());
            stmt.setString(12, m.getDescripcion());
            stmt.setInt(13, m.getId_Cedente());
            stmt.setInt(14, id);
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String query = "DELETE FROM mascota WHERE id_mascotas = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Mascota mapResult(ResultSet rs) throws SQLException {
        Mascota m = new Mascota();
        m.setId_mascotas(rs.getInt("id_mascotas"));
        m.setNombre_mascotas(rs.getString("nombre_mascotas"));
        m.setCodigo_especie(rs.getInt("codigo_especie"));
        m.setSexo(rs.getString("sexo"));
        m.setPeso(rs.getFloat("peso"));
        m.setCodigo_tamaño(rs.getInt("codigo_tamaño"));
        m.setRaza(rs.getString("raza"));
        m.setEsterilizado(rs.getString("esterilizado"));
        m.setDesparasitado(rs.getString("desparasitado"));
        m.setDiscapacitado(rs.getString("discapacitado"));
        m.setEnfermedades(rs.getString("enfermedades"));
        m.setCodigo_vacunas(rs.getInt("codigo_vacunas"));
        m.setDescripcion(rs.getString("descripcion"));
        m.setId_Cedente(rs.getInt("id_Cedente"));
        return m;
    }
}
