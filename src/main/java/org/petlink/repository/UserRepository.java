package org.petlink.repository;

import org.petlink.config.DatabaseConfig;
import org.petlink.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public List<User> findAll() throws Exception {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM usuario";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapResult(rs));
            }
        }

        return users;
    }

    public User findById(int id) throws Exception {
        String query = "SELECT * FROM usuario WHERE id_usuario = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResult(rs);
            } else {
                return null;
            }
        }
    }

    public User findByCorreo(String correo) throws Exception {
        String query = "SELECT * FROM usuario WHERE correo = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResult(rs);
            } else {
                return null;
            }
        }
    }

    public void save(User user) throws Exception {
        String query = "INSERT INTO usuario (tipo_usuario, nombres, apellido_materno, apellido_paterno, edad, correo, contraseña, INE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getTipo_usuario());
            stmt.setString(2, user.getNombres());
            stmt.setString(3, user.getApellido_materno());
            stmt.setString(4, user.getApellido_paterno());
            stmt.setInt(5, user.getEdad());
            stmt.setString(6, user.getCorreo());
            stmt.setString(7, user.getContraseña());
            stmt.setString(8, user.getIne());

            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId_usuario(rs.getInt(1));
                }
            }
        }
    }

    public void update(int id, User user) throws Exception {
        String query = "UPDATE usuario SET tipo_usuario=?, nombres=?, apellido_materno=?, apellido_paterno=?, edad=?, correo=?, contraseña=?, INE=? WHERE id_usuario=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getTipo_usuario());
            stmt.setString(2, user.getNombres());
            stmt.setString(3, user.getApellido_materno());
            stmt.setString(4, user.getApellido_paterno());
            stmt.setInt(5, user.getEdad());
            stmt.setString(6, user.getCorreo());
            stmt.setString(7, user.getContraseña());
            stmt.setString(8, user.getIne());
            stmt.setInt(9, id);

            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String query = "DELETE FROM usuario WHERE id_usuario = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private User mapResult(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId_usuario(rs.getInt("id_usuario"));
        user.setTipo_usuario(rs.getString("tipo_usuario"));
        user.setNombres(rs.getString("nombres"));
        user.setApellido_materno(rs.getString("apellido_materno"));
        user.setApellido_paterno(rs.getString("apellido_paterno"));
        user.setEdad(rs.getInt("edad"));
        user.setCorreo(rs.getString("correo"));
        user.setContraseña(rs.getString("contraseña"));
        user.setIne(rs.getString("INE"));
        return user;
    }
}