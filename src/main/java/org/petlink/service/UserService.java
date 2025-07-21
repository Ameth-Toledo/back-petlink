package org.petlink.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.petlink.model.User;
import org.petlink.repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository repository = new UserRepository();

    public List<User> getAll() throws Exception {
        return repository.findAll();
    }

    public User getById(int id) throws Exception {
        return repository.findById(id);
    }

    public void create(User user) throws Exception {
        validateUser(user);

        String hashed = BCrypt.withDefaults().hashToString(12, user.getContraseña().toCharArray());
        user.setContraseña(hashed);

        repository.save(user);
    }

    public void update(int id, User user) throws Exception {
        validateUser(user);
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
        if (user.getCorreo() == null || user.getCorreo().isEmpty()) {
            throw new Exception("Correo es obligatorio");
        }
        if (user.getContraseña() == null || user.getContraseña().length() < 6) {
            throw new Exception("Contraseña debe tener al menos 6 caracteres");
        }
        if (user.getNombres() == null || user.getNombres().isEmpty()) {
            throw new Exception("Nombres son obligatorios");
        }
    }
}
