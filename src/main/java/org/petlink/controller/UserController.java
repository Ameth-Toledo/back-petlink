package org.petlink.controller;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.petlink.model.User;
import org.petlink.service.UserService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.security.Keys;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;

import java.io.IOException;
import java.io.InputStream;;


public class UserController {
    private final UserService service = new UserService();
    private final Key jwtSecret = Keys.hmacShaKeyFor("mi_clave_supersecreta_123456789012345".getBytes());


    public void getAll(Context ctx) throws Exception {
        List<User> users = service.getAll();
        ctx.json(users);
    }

    public void getById(Context ctx) throws Exception {
        int id = Integer.parseInt(ctx.pathParam("id"));
        User user = service.getById(id);
        if (user == null) {
            ctx.status(404).result("Usuario no encontrado");
        } else {
            ctx.json(user);
        }
    }

    public void create(Context ctx) {
        try {
            UploadedFile ineFile = ctx.uploadedFile("ine");
            
            if (ctx.formParam("tipo_usuario") == null || ctx.formParam("nombres") == null || 
                ctx.formParam("correo") == null || ctx.formParam("contraseña") == null) {
                ctx.status(400).json(Map.of("error", "Faltan campos obligatorios"));
                return;
            }
            
            User user = new User();
            user.setTipo_usuario(ctx.formParam("tipo_usuario"));
            user.setNombres(ctx.formParam("nombres"));
            user.setApellido_materno(ctx.formParam("apellido_materno"));
            user.setApellido_paterno(ctx.formParam("apellido_paterno"));
            user.setCorreo(ctx.formParam("correo"));
            user.setContraseña(ctx.formParam("contraseña"));
            
            if (ineFile != null) {
                String contentType = ineFile.contentType();
                if (!isValidImageFormat(contentType)) {
                    ctx.status(400).json(Map.of("error", "Formato de imagen no válido. Use JPG, JPEG o PNG"));
                    return;
                }
                
                String uploadDir = "src/main/resources/uploads/ines/";
                Files.createDirectories(Paths.get(uploadDir));
                
                String fileName = "ine_" + System.currentTimeMillis() + getFileExtension(ineFile.filename());
                Path filePath = Paths.get(uploadDir + fileName);
                
                Files.copy(ineFile.content(), filePath);
                user.setIne("/uploads/ines/" + fileName);
            }

            service.create(user);
            ctx.status(201).json(user);
        } catch (Exception e) {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        }
    }

    private boolean isValidImageFormat(String contentType) {
        return contentType != null && (
            contentType.equals("image/jpeg") || 
            contentType.equals("image/jpg") || 
            contentType.equals("image/png")
        );
    }

    private String getFileExtension(String filename) {
        try {
            String extension = filename.substring(filename.lastIndexOf(".")).toLowerCase();
            if (extension.equals(".jpeg")) return ".jpg";
            return extension;
        } catch (Exception e) {
            return ".jpg";
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            
            User userActual = service.getById(id);
            if (userActual == null) {
                ctx.status(404).json(Map.of("error", "Usuario no encontrado"));
                return;
            }

            if (ctx.formParam("tipo_usuario") != null) {
                userActual.setTipo_usuario(ctx.formParam("tipo_usuario"));
            }
            if (ctx.formParam("nombres") != null) {
                userActual.setNombres(ctx.formParam("nombres"));
            }
            if (ctx.formParam("apellido_paterno") != null) {
                userActual.setApellido_paterno(ctx.formParam("apellido_paterno"));
            }
            if (ctx.formParam("apellido_materno") != null) {
                userActual.setApellido_materno(ctx.formParam("apellido_materno"));
            }
            if (ctx.formParam("correo") != null) {
                userActual.setCorreo(ctx.formParam("correo"));
            }
            if (ctx.formParam("contraseña") != null) {
                userActual.setContraseña(ctx.formParam("contraseña"));
            }

            if (ctx.isMultipartFormData()) {
                UploadedFile nuevaIne = ctx.uploadedFile("ine");
                
                if (nuevaIne != null) {
                    String contentType = nuevaIne.contentType();
                    if (!isValidImageFormat(contentType)) {
                        ctx.status(400).json(Map.of("error", "Formato de imagen no válido. Use JPG, JPEG o PNG"));
                        return;
                    }

                    if (userActual.getIne() != null && !userActual.getIne().isEmpty()) {
                        try {
                            Path pathAnterior = Paths.get("src/main/resources/uploads" + userActual.getIne());
                            Files.deleteIfExists(pathAnterior);
                        } catch (IOException e) {
                            System.err.println("Error al eliminar el INE anterior: " + e.getMessage());
                        }
                    }

                    String uploadDir = "src/main/resources/uploads/ines/";
                    Files.createDirectories(Paths.get(uploadDir));
                    
                    String fileName = "ine_" + System.currentTimeMillis() + getFileExtension(nuevaIne.filename());
                    Path filePath = Paths.get(uploadDir + fileName);
                    
                    try (InputStream is = nuevaIne.content()) {
                        Files.copy(is, filePath);
                    }

                    String filePathUrl = "/ines/" + fileName;
                    userActual.setIne(filePathUrl);
                }
            }

            service.update(id, userActual);
            
            ctx.json(Map.of(
                "success", true,
                "message", "Usuario actualizado",
                "ine_url", userActual.getIne(),
                "usuario", userActual 
            ));
            
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "ID de usuario inválido"));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", "Error interno: " + e.getMessage()));
            e.printStackTrace();
        }
    }

    public void delete(Context ctx) throws Exception {
        int id = Integer.parseInt(ctx.pathParam("id"));
        service.delete(id);
        ctx.status(204);
    }

    public void login(Context ctx) {
        try {
            User body = ctx.bodyAsClass(User.class);
            User user = service.login(body.getCorreo(), body.getContraseña());
            if (user != null) {
                String token = generateToken(user);
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("usuario", user);
                ctx.status(200).json(response);
            } else {
                ctx.status(401).result("Credenciales inválidas");
            }
        } catch (Exception e) {
            ctx.status(400).result("Error: " + e.getMessage());
        }
    }

    private String generateToken(User user) {
    return Jwts.builder()
            .setSubject(user.getCorreo())
            .claim("id", user.getId_usuario())
            .claim("tipo", user.getTipo_usuario())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000))
            .signWith(jwtSecret, SignatureAlgorithm.HS256)
            .compact();
}
}
