package org.petlink.controller;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import org.petlink.model.SolicitudCedente;
import org.petlink.service.SolicitudCedenteService;

import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SolicitudCedenteController {

    private final SolicitudCedenteService service = new SolicitudCedenteService();

    public void getAll(Context ctx) {
        try {
            List<SolicitudCedente> solicitudes = service.getAll();
            ctx.json(solicitudes);
        } catch (Exception e) {
            ctx.status(500).result("Error al obtener las solicitudes: " + e.getMessage());
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            SolicitudCedente s = service.getById(id);
            if (s != null) {
                ctx.json(s);
            } else {
                ctx.status(404).result("Solicitud no encontrada");
            }
        } catch (Exception e) {
            ctx.status(400).result("Error: " + e.getMessage());
        }
    }

    public void create(Context ctx) {
        try {
            if (!ctx.isMultipartFormData()) {
                ctx.status(400).json(Map.of("error", "Debe usar Content-Type: multipart/form-data"));
                return;
            }

            String codigoUsuarioStr = ctx.formParam("codigo_usuario");
            List<UploadedFile> fotosMascotas = ctx.uploadedFiles("fotos_mascotas");

            if (codigoUsuarioStr == null || codigoUsuarioStr.trim().isEmpty()) {
                ctx.status(400).json(Map.of("error", "codigo_usuario es requerido"));
                return;
            }

            if (fotosMascotas == null || fotosMascotas.size() != 3) {
                ctx.status(400).json(Map.of("error", "Se requieren exactamente 3 fotos de la mascota"));
                return;
            }

            int codigoUsuario;
            try {
                codigoUsuario = Integer.parseInt(codigoUsuarioStr.trim());
            } catch (NumberFormatException e) {
                ctx.status(400).json(Map.of("error", "codigo_usuario debe ser un número"));
                return;
            }

            String uploadDir = "src/main/resources/uploads/cedente/";
            Files.createDirectories(Paths.get(uploadDir));
            
            List<String> fileUrls = new ArrayList<>();
            for (int i = 0; i < fotosMascotas.size(); i++) {
                UploadedFile foto = fotosMascotas.get(i);
                
                if (!isValidImageFormat(foto.contentType())) {
                    ctx.status(400).json(Map.of("error", "Formato de imagen no válido. Use JPG, JPEG o PNG"));
                    return;
                }

                String extension = foto.filename().substring(
                    foto.filename().lastIndexOf(".")
                ).toLowerCase();
                
                String fileName = "mascota_" + System.currentTimeMillis() + "_" + i + extension;
                Path filePath = Paths.get(uploadDir + fileName); 
                String fileUrl = "/uploads/cedente/" + fileName;
                
                try (InputStream is = foto.content()) {
                    Files.copy(is, filePath); 
                }
                
                fileUrls.add(fileUrl);
            }

            SolicitudCedente solicitud = new SolicitudCedente();
            solicitud.setEstado_solicitudCedente("Pendiente");
            solicitud.setFotos_mascotas(fileUrls);
            solicitud.setCodigo_usuario(codigoUsuario);
            
            service.create(solicitud);
            
            ctx.status(201).json(Map.of(
                "success", true,
                "message", "Solicitud registrada",
                "id", solicitud.getId_solicitudCedente(),
                "fotos_urls", fileUrls
            ));
            
        } catch (IOException e) {
            ctx.status(500).json(Map.of("error", "Error al guardar archivos: " + e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", "Error interno del servidor"));
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
            
            SolicitudCedente solicitudActual = service.getById(id);
            if (solicitudActual == null) {
                ctx.status(404).json(Map.of("error", "Solicitud no encontrada"));
                return;
            }

            if (ctx.formParam("estado_solicitudCedente") != null) {
                solicitudActual.setEstado_solicitudCedente(ctx.formParam("estado_solicitudCedente"));
            }

            if (ctx.isMultipartFormData()) {
                List<UploadedFile> nuevasFotos = ctx.uploadedFiles("fotos_mascotas");
                
                if (nuevasFotos != null && !nuevasFotos.isEmpty()) {
                    // Verificar que sean exactamente 3 fotos
                    if (nuevasFotos.size() != 3) {
                        ctx.status(400).json(Map.of("error", "Se requieren exactamente 3 fotos de la mascota"));
                        return;
                    }

                    // Eliminar fotos antiguas
                    if (solicitudActual.getFotos_mascotas() != null) {
                        for (String fotoUrl : solicitudActual.getFotos_mascotas()) {
                            try {
                                Path pathAnterior = Paths.get("src/main/resources" + fotoUrl);
                                Files.deleteIfExists(pathAnterior);
                            } catch (IOException e) {
                                System.err.println("Error al eliminar la imagen anterior: " + e.getMessage());
                            }
                        }
                    }

                    String uploadDir = "src/main/resources/uploads/cedente/";
                    Files.createDirectories(Paths.get(uploadDir));
                    
                    List<String> nuevasUrls = new ArrayList<>();
                    for (int i = 0; i < nuevasFotos.size(); i++) {
                        UploadedFile foto = nuevasFotos.get(i);
                        String contentType = foto.contentType();
                        
                        if (!isValidImageFormat(contentType)) {
                            ctx.status(400).json(Map.of("error", "Formato de imagen no válido. Use JPG, JPEG o PNG"));
                            return;
                        }

                        String fileName = "mascota_" + System.currentTimeMillis() + "_" + i + getFileExtension(foto.filename());
                        Path filePath = Paths.get(uploadDir + fileName);
                        
                        try (InputStream is = foto.content()) {
                            Files.copy(is, filePath);
                        }

                        nuevasUrls.add("/uploads/cedente/" + fileName);
                    }

                    solicitudActual.setFotos_mascotas(nuevasUrls);
                }
            }

            service.update(id, solicitudActual);
            
            ctx.json(Map.of(
                "success", true,
                "message", "Solicitud actualizada",
                "fotos_mascotas_urls", solicitudActual.getFotos_mascotas()
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
            ctx.status(400).result("Error al eliminar: " + e.getMessage());
        }
    }
}
