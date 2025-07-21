package org.petlink.controller;

import io.javalin.http.Context;
import java.io.IOException;  
import io.javalin.http.UploadedFile;
import org.petlink.model.SolicitudAdopcion;
import org.petlink.service.SolicitudAdopcionService;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import java.sql.Date;

public class SolicitudAdopcionController {
    
    private final SolicitudAdopcionService service = new SolicitudAdopcionService();

    public void getAll(Context ctx) {
        try {
            List<SolicitudAdopcion> list = service.getAll();
            ctx.json(list);
        } catch (Exception e) {
            ctx.status(500).result("Error al obtener solicitudes: " + e.getMessage());
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            SolicitudAdopcion s = service.getById(id);
            if (s != null) {
                ctx.json(s);
            } else {
                ctx.status(404).result("No encontrado");
            }
        } catch (Exception e) {
            ctx.status(400).result("ID inválido: " + e.getMessage());
        }
    }

    public void create(Context ctx) {
        try {
            if (!ctx.isMultipartFormData()) {
                ctx.status(400).json(Map.of("error", "Debe usar Content-Type: multipart/form-data"));
                return;
            }

            UploadedFile fotoVivienda = ctx.uploadedFile("foto_vivienda");
            
            if (fotoVivienda == null) {
                ctx.status(400).json(Map.of("error", "La foto de la vivienda es obligatoria"));
                return;
            }

            String contentType = fotoVivienda.contentType();
            if (!isValidImageFormat(contentType)) {
                ctx.status(400).json(Map.of("error", "Formato de imagen no válido. Use JPG, JPEG o PNG"));
                return;
            }

            String uploadDir = "src/main/resources/uploads/adopcion/";
            Files.createDirectories(Paths.get(uploadDir));
            
            String fileName = "vivienda_" + System.currentTimeMillis() + getFileExtension(fotoVivienda.filename());
            Path filePath = Paths.get(uploadDir + fileName);
            
            try (InputStream is = fotoVivienda.content()) {
                Files.copy(is, filePath);
            }

            SolicitudAdopcion solicitud = new SolicitudAdopcion();
            solicitud.setOcupacion_usuario(ctx.formParam("ocupacion_usuario"));
            solicitud.setTipo_vivienda(ctx.formParam("tipo_vivienda"));
            solicitud.setMascotas_previas(ctx.formParam("mascotas_previas"));
            solicitud.setEstado_vivienda(ctx.formParam("estado_vivienda"));
            solicitud.setPermisopara_mascotas(ctx.formParam("permisopara_mascotas"));
            solicitud.setNumero_personas(Integer.parseInt(ctx.formParam("numero_personas")));
            solicitud.setNiños_casa(ctx.formParam("niños_casa"));
            solicitud.setExperiencia_mascotas(ctx.formParam("experiencia_mascotas"));
            solicitud.setEstado_solicitudAdopcion("Pendiente");
            solicitud.setCodigo_usuario(Integer.parseInt(ctx.formParam("codigo_usuario")));
            solicitud.setFecha_solicitudAdopcion(new Date(System.currentTimeMillis()));
            
            String imagePath = "/uploads/adopcion/" + fileName;
            solicitud.setFoto_vivienda(imagePath);

            int idGenerado = service.createAndReturnId(solicitud);
            
            ctx.status(201).json(Map.of(
                "success", true,
                "message", "Solicitud registrada",
                "id_solicitudAdopcion", idGenerado,
                "foto_vivienda_url", imagePath
            ));
            
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "Número inválido en campo numérico"));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", "Error interno: " + e.getMessage()));
            e.printStackTrace();
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
            
            SolicitudAdopcion solicitudActual = service.getById(id);
            if (solicitudActual == null) {
                ctx.status(404).json(Map.of("error", "Solicitud no encontrada"));
                return;
            }

            if (ctx.formParam("ocupacion_usuario") != null) {
                solicitudActual.setOcupacion_usuario(ctx.formParam("ocupacion_usuario"));
            }
            if (ctx.formParam("tipo_vivienda") != null) {
                solicitudActual.setTipo_vivienda(ctx.formParam("tipo_vivienda"));
            }
            if (ctx.formParam("mascotas_previas") != null) {
                solicitudActual.setMascotas_previas(ctx.formParam("mascotas_previas"));
            }
            if (ctx.formParam("estado_vivienda") != null) {
                solicitudActual.setEstado_vivienda(ctx.formParam("estado_vivienda"));
            }
            if (ctx.formParam("permisopara_mascotas") != null) {
                solicitudActual.setPermisopara_mascotas(ctx.formParam("permisopara_mascotas"));
            }
            if (ctx.formParam("numero_personas") != null) {
                solicitudActual.setNumero_personas(Integer.parseInt(ctx.formParam("numero_personas")));
            }
            if (ctx.formParam("niños_casa") != null) {
                solicitudActual.setNiños_casa(ctx.formParam("niños_casa"));
            }
            if (ctx.formParam("experiencia_mascotas") != null) {
                solicitudActual.setExperiencia_mascotas(ctx.formParam("experiencia_mascotas"));
            }

            if (ctx.isMultipartFormData()) {
                UploadedFile nuevaFoto = ctx.uploadedFile("foto_vivienda");
                
                if (nuevaFoto != null) {
                    String contentType = nuevaFoto.contentType();
                    if (!isValidImageFormat(contentType)) {
                        ctx.status(400).json(Map.of("error", "Formato de imagen no válido. Use JPG, JPEG o PNG"));
                        return;
                    }

                    if (solicitudActual.getFoto_vivienda() != null && !solicitudActual.getFoto_vivienda().isEmpty()) {
                        try {
                            Path pathAnterior = Paths.get("src/main/resources" + solicitudActual.getFoto_vivienda());
                            Files.deleteIfExists(pathAnterior);
                        } catch (IOException e) {
                            System.err.println("Error al eliminar la imagen anterior: " + e.getMessage());
                        }
                    }

                    String uploadDir = "src/main/resources/uploads/adopcion/";
                    Files.createDirectories(Paths.get(uploadDir));
                    
                    String fileName = "vivienda_" + System.currentTimeMillis() + getFileExtension(nuevaFoto.filename());
                    Path filePath = Paths.get(uploadDir + fileName);
                    
                    try (InputStream is = nuevaFoto.content()) {
                        Files.copy(is, filePath);
                    }

                    String imagePath = "/uploads/adopcion/" + fileName;
                    solicitudActual.setFoto_vivienda(imagePath);
                }
            }

            service.update(id, solicitudActual);
            
            ctx.json(Map.of(
                "success", true,
                "message", "Solicitud actualizada",
                "foto_vivienda_url", solicitudActual.getFoto_vivienda()
            ));
            
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "Número inválido en campo numérico"));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", "Error interno: " + e.getMessage()));
            e.printStackTrace();
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            service.delete(id);
            ctx.status(204);
        } catch (Exception e) {
            ctx.status(400).result("Error: " + e.getMessage());
        }
    }
}
