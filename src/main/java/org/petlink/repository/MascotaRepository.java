package org.petlink.repository;

import org.petlink.config.DatabaseConfig;
import org.petlink.model.Mascota;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MascotaRepository {

    public List<Mascota> findAll() throws SQLException {
        List<Mascota> mascotas = new ArrayList<>();
        String query = "SELECT * FROM mascota ORDER BY fecha_registro DESC"; // Ordenar por fecha más reciente

        try {
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    mascotas.add(mapResultSetToMascota(rs));
                }
            }
        } catch (Exception e) {
            throw new SQLException("Error al obtener todas las mascotas", e);
        }
        return mascotas;
    }

    public Mascota findById(int id) throws SQLException {
        String query = "SELECT * FROM mascota WHERE id_mascota = ?";
        
        try {
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return mapResultSetToMascota(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new SQLException("Error al buscar mascota por ID: " + id, e);
        }
        return null;
    }

    public int save(Mascota mascota) throws SQLException {
        String query = "INSERT INTO mascota (nombre, especie, sexo, peso, tamaño, esterilizado, " +
                      "discapacitado, desparasitado, vacunas, descripcion, fotos_mascota, id_cedente, estado) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                
                setMascotaParameters(stmt, mascota);
                stmt.executeUpdate();
                
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        // Recuperar la mascota completa con la fecha de registro
                        return id;
                    }
                }
            }
        } catch (Exception e) {
            throw new SQLException("Error al guardar mascota", e);
        }
        throw new SQLException("No se pudo guardar la mascota, no se obtuvo ID");
    }

    public void update(Mascota mascota) throws SQLException {
        String query = "UPDATE mascota SET nombre = ?, especie = ?, sexo = ?, peso = ?, " +
                      "tamaño = ?, esterilizado = ?, discapacitado = ?, desparasitado = ?, " +
                      "vacunas = ?, descripcion = ?, fotos_mascota = ?, id_cedente = ?, estado = ? " +
                      "WHERE id_mascota = ?";
        
        try {
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                
                setMascotaParameters(stmt, mascota);
                stmt.setInt(14, mascota.getId());
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            throw new SQLException("Error al actualizar mascota con ID: " + mascota.getId(), e);
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM mascota WHERE id_mascota = ?";
        
        try {
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            throw new SQLException("Error al eliminar mascota con ID: " + id, e);
        }
    }

    private void setMascotaParameters(PreparedStatement stmt, Mascota mascota) throws SQLException {
        stmt.setString(1, mascota.getNombre());
        stmt.setString(2, mascota.getEspecie());
        stmt.setString(3, mascota.getSexo());
        stmt.setFloat(4, mascota.getPeso());
        stmt.setString(5, mascota.getTamaño());
        stmt.setString(6, mascota.getEsterilizado());
        stmt.setString(7, mascota.getDiscapacitado());
        stmt.setString(8, mascota.getDesparasitado());
        stmt.setString(9, mascota.getVacunas());
        stmt.setString(10, mascota.getDescripcion());
        stmt.setString(11, mascota.getFotosAsString());
        stmt.setInt(12, mascota.getIdCedente());
        stmt.setString(13, mascota.getEstado());
    }

    private Mascota mapResultSetToMascota(ResultSet rs) throws SQLException {
        Mascota mascota = new Mascota();
        mascota.setId(rs.getInt("id_mascota"));
        mascota.setNombre(rs.getString("nombre"));
        mascota.setEspecie(rs.getString("especie"));
        mascota.setSexo(rs.getString("sexo"));
        mascota.setPeso(rs.getFloat("peso"));
        mascota.setTamaño(rs.getString("tamaño"));
        mascota.setEsterilizado(rs.getString("esterilizado"));
        mascota.setDiscapacitado(rs.getString("discapacitado"));
        mascota.setDesparasitado(rs.getString("desparasitado"));
        mascota.setVacunas(rs.getString("vacunas"));
        mascota.setDescripcion(rs.getString("descripcion"));
        mascota.setFotosFromString(rs.getString("fotos_mascota"));
        mascota.setIdCedente(rs.getInt("id_cedente"));
        mascota.setEstado(rs.getString("estado"));
        
        // Mapear la fecha de registro
        Timestamp fechaTimestamp = rs.getTimestamp("fecha_registro");
        if (fechaTimestamp != null) {
            mascota.setFechaRegistro(fechaTimestamp.toLocalDateTime());
        }
        
        return mascota;
    }
}