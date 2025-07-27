package org.petlink.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.petlink.model.User;
import org.petlink.repository.UserRepository;

import java.util.List;
import java.util.regex.Pattern;

public class UserService {
    private final UserRepository repository = new UserRepository();
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public List<User> getAll() throws Exception {
        return repository.findAll();
    }

    public User getById(int id) throws Exception {
        return repository.findById(id);
    }

    public User findByCorreo(String correo) throws Exception {
        return repository.findByCorreo(correo);
    }

    public void create(User user) throws Exception {
        validateUser(user);
        
        // Verificar si el correo ya existe
        if (repository.findByCorreo(user.getCorreo()) != null) {
            throw new Exception("El correo ya está registrado");
        }

        // Validar edad mínima
        if (user.getEdad() < 18) {
            throw new Exception("Debe ser mayor de 18 años");
        }

        // Encriptar contraseña
        String hashed = BCrypt.withDefaults().hashToString(12, user.getContraseña().toCharArray());
        user.setContraseña(hashed);

        repository.save(user);
    }

    public void update(int id, User user) throws Exception {
        validateUser(user);
        
        // Verificar si el usuario existe
        User existingUser = repository.findById(id);
        if (existingUser == null) {
            throw new Exception("Usuario no encontrado");
        }

        // Verificar si el correo ya está en uso por otro usuario
        User userWithSameEmail = repository.findByCorreo(user.getCorreo());
        if (userWithSameEmail != null && userWithSameEmail.getId_usuario() != id) {
            throw new Exception("El correo ya está registrado por otro usuario");
        }

        // Validar edad mínima
        if (user.getEdad() < 18) {
            throw new Exception("Debe ser mayor de 18 años");
        }

        // Si la contraseña cambió, encriptarla
        if (!user.getContraseña().equals(existingUser.getContraseña())) {
            String hashed = BCrypt.withDefaults().hashToString(12, user.getContraseña().toCharArray());
            user.setContraseña(hashed);
        }

        repository.update(id, user);
    }

    public void delete(int id) throws Exception {
        repository.delete(id);
    }

    public User login(String correo, String contraseña) throws Exception {
        User user = repository.findByCorreo(correo);
        if (user != null) {
            boolean valid = BCrypt.verifyer().verify(contraseña.toCharArray(), user.getContraseña()).verified;
            if (valid) {
                return user;
            }
        }
        return null;
    }

    private void validateUser(User user) throws Exception {
        if (user.getTipo_usuario() == null || user.getTipo_usuario().isEmpty()) {
            throw new Exception("Tipo de usuario es obligatorio");
        }
        if (user.getNombres() == null || user.getNombres().isEmpty()) {
            throw new Exception("Nombres son obligatorios");
        }
        if (user.getApellido_paterno() == null || user.getApellido_paterno().isEmpty()) {
            throw new Exception("Apellido paterno es obligatorio");
        }
        if (user.getEdad() <= 0) {
            throw new Exception("Edad debe ser un número positivo");
        }
        if (user.getCorreo() == null || user.getCorreo().isEmpty()) {
            throw new Exception("Correo es obligatorio");
        }
        if (!EMAIL_PATTERN.matcher(user.getCorreo()).matches()) {
            throw new Exception("Correo electrónico no válido");
        }
        if (user.getContraseña() == null || user.getContraseña().length() < 6) {
            throw new Exception("Contraseña debe tener al menos 6 caracteres");
        }
    }
}