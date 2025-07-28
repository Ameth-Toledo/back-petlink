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
            ctx.status(500).json(Map.of("error", "Error al obtener solicitudes: " + e.getMessage()));
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            SolicitudAdopcion s = service.getById(id);
            if (s != null) {
                ctx.json(s);
            } else {
                ctx.status(404).json(Map.of("error", "Solicitud no encontrada"));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "ID inválido"));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", "Error interno: " + e.getMessage()));
        }
    }

    public void create(Context ctx) {
        try {
            if (!ctx.isMultipartFormData()) {
                ctx.status(400).json(Map.of("error", "Debe usar Content-Type: multipart/form-data"));
                return;
            }

            // Procesar archivos
            UploadedFile ineFile = ctx.uploadedFile("ine_document");
            UploadedFile espacioFile = ctx.uploadedFile("espacio_mascota");
            
            if (ineFile == null || espacioFile == null) {
                ctx.status(400).json(Map.of("error", "Ambos documentos (INE y espacio mascota) son obligatorios"));
                return;
            }

            // Validar formatos de archivos
            if (!isValidImageFormat(ineFile.contentType()) || !isValidImageFormat(espacioFile.contentType())) {
                ctx.status(400).json(Map.of("error", "Formatos de imagen no válidos. Use JPG, JPEG o PNG"));
                return;
            }

            // Guardar archivos
            String uploadDir = "src/main/resources/uploads/adopcion/";
            Files.createDirectories(Paths.get(uploadDir));
            
            String ineFileName = "ine_" + System.currentTimeMillis() + getFileExtension(ineFile.filename());
            String espacioFileName = "espacio_" + System.currentTimeMillis() + getFileExtension(espacioFile.filename());
            
            saveUploadedFile(ineFile, uploadDir + ineFileName);
            saveUploadedFile(espacioFile, uploadDir + espacioFileName);

            // Crear solicitud
            SolicitudAdopcion solicitud = new SolicitudAdopcion();
            solicitud.setMascotaId(ctx.formParam("mascota_id"));
            solicitud.setAdoptanteId(ctx.formParam("adoptante_id"));
            solicitud.setNombre(ctx.formParam("nombre"));
            solicitud.setApellidoPaterno(ctx.formParam("apellido_paterno"));
            solicitud.setApellidoMaterno(ctx.formParam("apellido_materno"));
            solicitud.setEdad(Integer.parseInt(ctx.formParam("edad")));
            solicitud.setCorreo(ctx.formParam("correo"));
            solicitud.setOcupacion(ctx.formParam("ocupacion"));
            solicitud.setPersonasVivienda(Integer.parseInt(ctx.formParam("personas_vivienda")));
            solicitud.setHayNinos(ctx.formParam("hay_ninos"));
            solicitud.setPermiteMascotas(ctx.formParam("permite_mascotas"));
            solicitud.setTipoVivienda(ctx.formParam("tipo_vivienda"));
            solicitud.setTipoPropiedad(ctx.formParam("tipo_propiedad"));
            solicitud.setExperiencia(ctx.formParam("experiencia"));
            solicitud.setHistorialMascotas(ctx.formParam("historial_mascotas"));
            solicitud.setIneDocument("/uploads/adopcion/" + ineFileName);
            solicitud.setEspacioMascota("/uploads/adopcion/" + espacioFileName);
            solicitud.setFechaSolicitud(new Date(System.currentTimeMillis()));
            solicitud.setEstadoSolicitud("pendiente");

            int idGenerado = service.createAndReturnId(solicitud);
            
            ctx.status(201).json(Map.of(
                "success", true,
                "message", "Solicitud registrada",
                "id_solicitud", idGenerado,
                "ine_url", solicitud.getIneDocument(),
                "espacio_url", solicitud.getEspacioMascota()
            ));
            
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "Número inválido en campo numérico"));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", "Error interno: " + e.getMessage()));
            e.printStackTrace();
        }
    }

    private void saveUploadedFile(UploadedFile file, String filePath) throws IOException {
        try (InputStream is = file.content()) {
            Files.copy(is, Paths.get(filePath));
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

            // Actualizar campos básicos
            if (ctx.formParam("nombre") != null) solicitudActual.setNombre(ctx.formParam("nombre"));
            if (ctx.formParam("apellido_paterno") != null) solicitudActual.setApellidoPaterno(ctx.formParam("apellido_paterno"));
            if (ctx.formParam("apellido_materno") != null) solicitudActual.setApellidoMaterno(ctx.formParam("apellido_materno"));
            if (ctx.formParam("edad") != null) solicitudActual.setEdad(Integer.parseInt(ctx.formParam("edad")));
            if (ctx.formParam("correo") != null) solicitudActual.setCorreo(ctx.formParam("correo"));
            if (ctx.formParam("ocupacion") != null) solicitudActual.setOcupacion(ctx.formParam("ocupacion"));
            if (ctx.formParam("personas_vivienda") != null) solicitudActual.setPersonasVivienda(Integer.parseInt(ctx.formParam("personas_vivienda")));
            if (ctx.formParam("hay_ninos") != null) solicitudActual.setHayNinos(ctx.formParam("hay_ninos"));
            if (ctx.formParam("permite_mascotas") != null) solicitudActual.setPermiteMascotas(ctx.formParam("permite_mascotas"));
            if (ctx.formParam("tipo_vivienda") != null) solicitudActual.setTipoVivienda(ctx.formParam("tipo_vivienda"));
            if (ctx.formParam("tipo_propiedad") != null) solicitudActual.setTipoPropiedad(ctx.formParam("tipo_propiedad"));
            if (ctx.formParam("experiencia") != null) solicitudActual.setExperiencia(ctx.formParam("experiencia"));
            if (ctx.formParam("historial_mascotas") != null) solicitudActual.setHistorialMascotas(ctx.formParam("historial_mascotas"));
            if (ctx.formParam("estado_solicitud") != null) solicitudActual.setEstadoSolicitud(ctx.formParam("estado_solicitud"));

            // Procesar archivos si se enviaron
            if (ctx.isMultipartFormData()) {
                processFileUpdate(ctx, "ine_document", solicitudActual::getIneDocument, solicitudActual::setIneDocument);
                processFileUpdate(ctx, "espacio_mascota", solicitudActual::getEspacioMascota, solicitudActual::setEspacioMascota);
            }

            service.update(id, solicitudActual);
            
            ctx.json(Map.of(
                "success", true,
                "message", "Solicitud actualizada",
                "solicitud", solicitudActual
            ));
            
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "Número inválido en campo numérico"));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", "Error interno: " + e.getMessage()));
            e.printStackTrace();
        }
    }

    private void processFileUpdate(Context ctx, String fieldName, 
                                 java.util.function.Supplier<String> getCurrentPath, 
                                 java.util.function.Consumer<String> setNewPath) throws IOException {
        UploadedFile newFile = ctx.uploadedFile(fieldName);
        if (newFile != null) {
            if (!isValidImageFormat(newFile.contentType())) {
                throw new IllegalArgumentException("Formato de imagen no válido para " + fieldName);
            }

            // Eliminar archivo anterior si existe
            String currentPath = getCurrentPath.get();
            if (currentPath != null && !currentPath.isEmpty()) {
                try {
                    Path pathAnterior = Paths.get("src/main/resources" + currentPath);
                    Files.deleteIfExists(pathAnterior);
                } catch (IOException e) {
                    System.err.println("Error al eliminar la imagen anterior: " + e.getMessage());
                }
            }

            // Guardar nuevo archivo
            String uploadDir = "src/main/resources/uploads/adopcion/";
            String fileName = fieldName + "_" + System.currentTimeMillis() + getFileExtension(newFile.filename());
            saveUploadedFile(newFile, uploadDir + fileName);
            
            // Actualizar path
            setNewPath.accept("/uploads/adopcion/" + fileName);
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            // Primero obtenemos la solicitud para eliminar los archivos asociados
            SolicitudAdopcion solicitud = service.getById(id);
            if (solicitud != null) {
                deleteFileIfExists(solicitud.getIneDocument());
                deleteFileIfExists(solicitud.getEspacioMascota());
            }
            service.delete(id);
            ctx.status(204);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "ID inválido"));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", "Error interno: " + e.getMessage()));
        }
    }

    private void deleteFileIfExists(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            try {
                Path path = Paths.get("src/main/resources" + filePath);
                Files.deleteIfExists(path);
            } catch (IOException e) {
                System.err.println("Error al eliminar archivo: " + e.getMessage());
            }
        }
    }
}