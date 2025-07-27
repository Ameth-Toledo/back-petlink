package org.petlink.controller;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.petlink.model.User;
import org.petlink.service.UserService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.util.*;

import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class UserController {
    private final UserService service = new UserService();
    private final Key jwtSecret = Keys.hmacShaKeyFor("mi_clave_supersecreta_123456789012345".getBytes());
    private static final String UPLOAD_DIR = "src/main/resources/uploads/ines/";

    public void getAll(Context ctx) {
        try {
            List<User> users = service.getAll();
            ctx.json(users);
        } catch (Exception e) {
            ctx.status(500).json(errorResponse("Error al obtener usuarios", e));
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            User user = service.getById(id);
            if (user != null) {
                ctx.json(user);
            } else {
                ctx.status(404).json(errorResponse("Usuario no encontrado", null));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(errorResponse("ID inválido", e));
        } catch (Exception e) {
            ctx.status(500).json(errorResponse("Error al buscar usuario", e));
        }
    }

    public void create(Context ctx) {
        try {
            if (!ctx.isMultipartFormData()) {
                ctx.status(400).json(Map.of("error", "Debe usar Content-Type: multipart/form-data"));
                return;
            }

            // Validar campos obligatorios
            Map<String, String> requiredFields = Map.of(
                "tipo_usuario", "Tipo de usuario es requerido",
                "nombres", "Nombres son requeridos",
                "apellido_paterno", "Apellido paterno es requerido",
                "edad", "Edad es requerida",
                "correo", "Correo es requerido",
                "contraseña", "Contraseña es requerida"
            );

            for (Map.Entry<String, String> entry : requiredFields.entrySet()) {
                if (ctx.formParam(entry.getKey()) == null || ctx.formParam(entry.getKey()).trim().isEmpty()) {
                    ctx.status(400).json(Map.of("error", entry.getValue()));
                    return;
                }
            }

            // Crear objeto usuario según tu modelo exacto
            User user = new User();
            user.setTipo_usuario(ctx.formParam("tipo_usuario"));
            user.setNombres(ctx.formParam("nombres"));
            user.setApellido_paterno(ctx.formParam("apellido_paterno"));
            user.setApellido_materno(ctx.formParam("apellido_materno"));
            user.setEdad(Integer.parseInt(ctx.formParam("edad")));
            user.setCorreo(ctx.formParam("correo"));
            user.setContraseña(ctx.formParam("contraseña"));

            // Procesar INE si se envió
            UploadedFile ineFile = ctx.uploadedFile("ine");
            if (ineFile != null) {
                if (!isValidImageFormat(ineFile.contentType())) {
                    ctx.status(400).json(Map.of("error", "Formato de INE no válido. Use JPG, JPEG o PNG"));
                    return;
                }

                Files.createDirectories(Paths.get(UPLOAD_DIR));
                
                String extension = getFileExtension(ineFile.filename());
                String fileName = "ine_" + System.currentTimeMillis() + extension;
                Path filePath = Paths.get(UPLOAD_DIR + fileName);
                
                try (InputStream is = ineFile.content()) {
                    Files.copy(is, filePath);
                }
                
                user.setIne("/uploads/ines/" + fileName);
            }

            service.create(user);
            
            ctx.status(201).json(Map.of(
                "success", true,
                "message", "Usuario registrado",
                "id", user.getId_usuario(),
                "ine_url", user.getIne()
            ));
            
        } catch (NumberFormatException e) {
            ctx.status(400).json(errorResponse("Formato numérico inválido (edad debe ser número)", e));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(errorResponse("Datos inválidos", e));
        } catch (IOException e) {
            ctx.status(500).json(errorResponse("Error al guardar archivos", e));
        } catch (Exception e) {
            ctx.status(500).json(errorResponse("Error interno del servidor", e));
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            
            if (!ctx.isMultipartFormData()) {
                ctx.status(400).json(Map.of("error", "Debe usar Content-Type: multipart/form-data"));
                return;
            }

            // Obtener usuario existente
            User userActual = service.getById(id);
            if (userActual == null) {
                ctx.status(404).json(Map.of("error", "Usuario no encontrado"));
                return;
            }

            // Actualizar campos
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
            if (ctx.formParam("edad") != null) {
                userActual.setEdad(Integer.parseInt(ctx.formParam("edad")));
            }
            if (ctx.formParam("correo") != null) {
                userActual.setCorreo(ctx.formParam("correo"));
            }
            if (ctx.formParam("contraseña") != null) {
                userActual.setContraseña(ctx.formParam("contraseña"));
            }

            // Procesar nueva INE si se envió
            UploadedFile nuevaIne = ctx.uploadedFile("ine");
            if (nuevaIne != null) {
                if (!isValidImageFormat(nuevaIne.contentType())) {
                    ctx.status(400).json(Map.of("error", "Formato de INE no válido. Use JPG, JPEG o PNG"));
                    return;
                }

                // Eliminar INE anterior si existe
                if (userActual.getIne() != null && !userActual.getIne().isEmpty()) {
                    try {
                        Path pathAnterior = Paths.get("src/main/resources" + userActual.getIne());
                        Files.deleteIfExists(pathAnterior);
                    } catch (IOException e) {
                        System.err.println("Error al eliminar el INE anterior: " + e.getMessage());
                    }
                }

                // Guardar nueva INE
                Files.createDirectories(Paths.get(UPLOAD_DIR));
                
                String extension = getFileExtension(nuevaIne.filename());
                String fileName = "ine_" + System.currentTimeMillis() + extension;
                Path filePath = Paths.get(UPLOAD_DIR + fileName);
                
                try (InputStream is = nuevaIne.content()) {
                    Files.copy(is, filePath);
                }
                
                userActual.setIne("/uploads/ines/" + fileName);
            }

            service.update(id, userActual);
            
            ctx.json(Map.of(
                "success", true,
                "message", "Usuario actualizado",
                "ine_url", userActual.getIne()
            ));
            
        } catch (NumberFormatException e) {
            ctx.status(400).json(errorResponse("ID inválido", e));
        } catch (Exception e) {
            ctx.status(500).json(errorResponse("Error al actualizar usuario", e));
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            User user = service.getById(id);
            
            if (user != null && user.getIne() != null) {
                try {
                    Path path = Paths.get("src/main/resources" + user.getIne());
                    Files.deleteIfExists(path);
                } catch (IOException e) {
                    System.err.println("Error al eliminar el INE: " + e.getMessage());
                }
            }
            
            service.delete(id);
            ctx.status(204);
        } catch (NumberFormatException e) {
            ctx.status(400).json(errorResponse("ID inválido", e));
        } catch (Exception e) {
            ctx.status(500).json(errorResponse("Error al eliminar usuario", e));
        }
    }

    public void login(Context ctx) {
        try {
            User body = ctx.bodyAsClass(User.class);
            if (body.getCorreo() == null || body.getCorreo().isEmpty() || 
                body.getContraseña() == null || body.getContraseña().isEmpty()) {
                ctx.status(400).json(Map.of("error", "Correo y contraseña son requeridos"));
                return;
            }

            User user = service.login(body.getCorreo(), body.getContraseña());
            if (user != null) {
                String token = generateToken(user);
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("usuario", user);
                ctx.status(200).json(response);
            } else {
                ctx.status(401).json(Map.of("error", "Credenciales inválidas"));
            }
        } catch (Exception e) {
            ctx.status(500).json(errorResponse("Error en el login", e));
        }
    }

    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getCorreo())
                .claim("id", user.getId_usuario())
                .claim("tipo", user.getTipo_usuario())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día
                .signWith(jwtSecret, SignatureAlgorithm.HS256)
                .compact();
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

    private static Map<String, String> errorResponse(String message, Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
        response.put("details", e != null ? e.getMessage() : null);
        return response;
    }
}