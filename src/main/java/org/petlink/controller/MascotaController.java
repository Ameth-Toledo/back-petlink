package org.petlink.controller;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import org.petlink.model.Mascota;
import org.petlink.service.MascotaService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MascotaController {
    private final MascotaService service = new MascotaService();
    private static final String UPLOAD_DIR = "src/main/resources/uploads/mascotas/";

    public void getAll(Context ctx) {
        try {
            List<Mascota> mascotas = service.getAllMascotas();
            // Convertir las mascotas a un formato que incluya la fecha formateada
            List<Map<String, Object>> mascotasResponse = new ArrayList<>();
            
            for (Mascota mascota : mascotas) {
                Map<String, Object> mascotaMap = new HashMap<>();
                mascotaMap.put("id", mascota.getId());
                mascotaMap.put("nombre", mascota.getNombre());
                mascotaMap.put("especie", mascota.getEspecie());
                mascotaMap.put("sexo", mascota.getSexo());
                mascotaMap.put("peso", mascota.getPeso());
                mascotaMap.put("tamaño", mascota.getTamaño());
                mascotaMap.put("esterilizado", mascota.getEsterilizado());
                mascotaMap.put("discapacitado", mascota.getDiscapacitado());
                mascotaMap.put("desparasitado", mascota.getDesparasitado());
                mascotaMap.put("vacunas", mascota.getVacunas());
                mascotaMap.put("descripcion", mascota.getDescripcion());
                mascotaMap.put("fotos_mascota", mascota.getFotos_mascota());
                mascotaMap.put("idCedente", mascota.getIdCedente());
                mascotaMap.put("estado", mascota.getEstado());
                mascotaMap.put("fechaRegistro", mascota.getFechaRegistroFormatted());
                
                mascotasResponse.add(mascotaMap);
            }
            
            ctx.json(mascotasResponse);
        } catch (Exception e) {
            ctx.status(500).json(errorResponse("Error al obtener mascotas", e));
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Mascota mascota = service.getMascotaById(id);
            if (mascota != null) {
                // Crear respuesta con fecha formateada
                Map<String, Object> response = new HashMap<>();
                response.put("id", mascota.getId());
                response.put("nombre", mascota.getNombre());
                response.put("especie", mascota.getEspecie());
                response.put("sexo", mascota.getSexo());
                response.put("peso", mascota.getPeso());
                response.put("tamaño", mascota.getTamaño());
                response.put("esterilizado", mascota.getEsterilizado());
                response.put("discapacitado", mascota.getDiscapacitado());
                response.put("desparasitado", mascota.getDesparasitado());
                response.put("vacunas", mascota.getVacunas());
                response.put("descripcion", mascota.getDescripcion());
                response.put("fotos_mascota", mascota.getFotos_mascota());
                response.put("idCedente", mascota.getIdCedente());
                response.put("estado", mascota.getEstado());
                response.put("fechaRegistro", mascota.getFechaRegistroFormatted());
                
                ctx.json(response);
            } else {
                ctx.status(404).json(errorResponse("Mascota no encontrada", null));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(errorResponse("ID inválido", e));
        } catch (Exception e) {
            ctx.status(500).json(errorResponse("Error al buscar mascota", e));
        }
    }

    public void create(Context ctx) {
        try {
            if (!ctx.isMultipartFormData()) {
                ctx.status(400).json(Map.of("error", "Debe usar Content-Type: multipart/form-data"));
                return;
            }

            // Obtener datos del formulario
            String nombre = ctx.formParam("nombre");
            String especie = ctx.formParam("especie");
            String sexo = ctx.formParam("sexo");
            float peso = Float.parseFloat(ctx.formParam("peso"));
            String tamaño = ctx.formParam("tamaño");
            String esterilizado = ctx.formParam("esterilizado");
            String discapacitado = ctx.formParam("discapacitado");
            String desparasitado = ctx.formParam("desparasitado");
            String vacunas = ctx.formParam("vacunas");
            String descripcion = ctx.formParam("descripcion");
            int idCedente = Integer.parseInt(ctx.formParam("idCedente"));
            String estado = ctx.formParam("estado") != null ? ctx.formParam("estado") : "disponible";

            // Procesar imágenes
            List<UploadedFile> fotosMascotas = ctx.uploadedFiles("fotos_mascota");

            if (fotosMascotas == null || fotosMascotas.size() < 3) {
                ctx.status(400).json(Map.of("error", "Se requieren al menos 3 fotos de la mascota"));
                return;
            }

            // Crear directorio si no existe
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            
            List<String> fileUrls = new ArrayList<>();
            for (int i = 0; i < fotosMascotas.size(); i++) {
                UploadedFile foto = fotosMascotas.get(i);
                
                if (!isValidImageFormat(foto.contentType())) {
                    ctx.status(400).json(Map.of("error", "Formato de imagen no válido. Use JPG, JPEG o PNG"));
                    return;
                }

                String extension = getFileExtension(foto.filename());
                String fileName = "mascota_" + System.currentTimeMillis() + "_" + i + extension;
                Path filePath = Paths.get(UPLOAD_DIR + fileName);
                String fileUrl = "/uploads/mascotas/" + fileName;
                
                try (InputStream is = foto.content()) {
                    Files.copy(is, filePath);
                }
                
                fileUrls.add(fileUrl);
            }

            // Crear objeto Mascota
            Mascota mascota = new Mascota(
                nombre, especie, sexo, peso, tamaño,
                esterilizado, discapacitado, desparasitado,
                vacunas, descripcion, fileUrls, idCedente, estado
            );

            Mascota nuevaMascota = service.createMascota(mascota);
            
            // Crear respuesta con datos de la mascota
            Map<String, Object> mascotaData = new HashMap<>();
            mascotaData.put("id", nuevaMascota.getId());
            mascotaData.put("nombre", nuevaMascota.getNombre());
            mascotaData.put("especie", nuevaMascota.getEspecie());
            mascotaData.put("sexo", nuevaMascota.getSexo());
            mascotaData.put("peso", nuevaMascota.getPeso());
            mascotaData.put("tamaño", nuevaMascota.getTamaño());
            mascotaData.put("esterilizado", nuevaMascota.getEsterilizado());
            mascotaData.put("discapacitado", nuevaMascota.getDiscapacitado());
            mascotaData.put("desparasitado", nuevaMascota.getDesparasitado());
            mascotaData.put("vacunas", nuevaMascota.getVacunas());
            mascotaData.put("descripcion", nuevaMascota.getDescripcion());
            mascotaData.put("fotos_mascota", nuevaMascota.getFotos_mascota());
            mascotaData.put("idCedente", nuevaMascota.getIdCedente());
            mascotaData.put("estado", nuevaMascota.getEstado());
            mascotaData.put("fechaRegistro", nuevaMascota.getFechaRegistroFormatted());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Mascota registrada");
            response.put("mascota", mascotaData);
            
            ctx.status(201).json(response);
            
        } catch (NumberFormatException e) {
            ctx.status(400).json(errorResponse("Formato numérico inválido", e));
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

            // Obtener datos del formulario
            String nombre = ctx.formParam("nombre");
            String especie = ctx.formParam("especie");
            String sexo = ctx.formParam("sexo");
            float peso = Float.parseFloat(ctx.formParam("peso"));
            String tamaño = ctx.formParam("tamaño");
            String esterilizado = ctx.formParam("esterilizado");
            String discapacitado = ctx.formParam("discapacitado");
            String desparasitado = ctx.formParam("desparasitado");
            String vacunas = ctx.formParam("vacunas");
            String descripcion = ctx.formParam("descripcion");
            int idCedente = Integer.parseInt(ctx.formParam("idCedente"));
            String estado = ctx.formParam("estado") != null ? ctx.formParam("estado") : "disponible";

            // Obtener mascota existente
            Mascota mascotaExistente = service.getMascotaById(id);
            if (mascotaExistente == null) {
                ctx.status(404).json(Map.of("error", "Mascota no encontrada"));
                return;
            }

            // Procesar nuevas imágenes si se enviaron
            List<String> fileUrls = mascotaExistente.getFotos_mascota();
            List<UploadedFile> nuevasFotos = ctx.uploadedFiles("fotos_mascota");
            
            if (nuevasFotos != null && !nuevasFotos.isEmpty()) {
                if (nuevasFotos.size() < 3) {
                    ctx.status(400).json(Map.of("error", "Se requieren al menos 3 fotos de la mascota"));
                    return;
                }

                // Eliminar imágenes antiguas
                if (fileUrls != null) {
                    for (String fotoUrl : fileUrls) {
                        try {
                            Path pathAnterior = Paths.get("src/main/resources" + fotoUrl);
                            Files.deleteIfExists(pathAnterior);
                        } catch (IOException e) {
                            System.err.println("Error al eliminar la imagen anterior: " + e.getMessage());
                        }
                    }
                }

                // Guardar nuevas imágenes
                fileUrls = new ArrayList<>();
                for (int i = 0; i < nuevasFotos.size(); i++) {
                    UploadedFile foto = nuevasFotos.get(i);
                    
                    if (!isValidImageFormat(foto.contentType())) {
                        ctx.status(400).json(Map.of("error", "Formato de imagen no válido. Use JPG, JPEG o PNG"));
                        return;
                    }

                    String extension = getFileExtension(foto.filename());
                    String fileName = "mascota_" + System.currentTimeMillis() + "_" + i + extension;
                    Path filePath = Paths.get(UPLOAD_DIR + fileName);
                    String fileUrl = "/uploads/mascotas/" + fileName;
                    
                    try (InputStream is = foto.content()) {
                        Files.copy(is, filePath);
                    }
                    
                    fileUrls.add(fileUrl);
                }
            }

            Mascota mascota = new Mascota(
                nombre, especie, sexo, peso, tamaño,
                esterilizado, discapacitado, desparasitado,
                vacunas, descripcion, fileUrls, idCedente, estado 
            );
            mascota.setId(id);
            
            Mascota mascotaActualizada = service.updateMascota(mascota);
            
            // Crear respuesta con datos de la mascota actualizada
            Map<String, Object> mascotaData = new HashMap<>();
            mascotaData.put("id", mascotaActualizada.getId());
            mascotaData.put("nombre", mascotaActualizada.getNombre());
            mascotaData.put("especie", mascotaActualizada.getEspecie());
            mascotaData.put("sexo", mascotaActualizada.getSexo());
            mascotaData.put("peso", mascotaActualizada.getPeso());
            mascotaData.put("tamaño", mascotaActualizada.getTamaño());
            mascotaData.put("esterilizado", mascotaActualizada.getEsterilizado());
            mascotaData.put("discapacitado", mascotaActualizada.getDiscapacitado());
            mascotaData.put("desparasitado", mascotaActualizada.getDesparasitado());
            mascotaData.put("vacunas", mascotaActualizada.getVacunas());
            mascotaData.put("descripcion", mascotaActualizada.getDescripcion());
            mascotaData.put("fotos_mascota", mascotaActualizada.getFotos_mascota());
            mascotaData.put("idCedente", mascotaActualizada.getIdCedente());
            mascotaData.put("estado", mascotaActualizada.getEstado());
            mascotaData.put("fechaRegistro", mascotaActualizada.getFechaRegistroFormatted());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Mascota actualizada");
            response.put("mascota", mascotaData);
            
            ctx.json(response);
            
        } catch (NumberFormatException e) {
            ctx.status(400).json(errorResponse("Formato numérico inválido", e));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(errorResponse("Datos inválidos", e));
        } catch (IOException e) {
            ctx.status(500).json(errorResponse("Error al guardar archivos", e));
        } catch (Exception e) {
            ctx.status(500).json(errorResponse("Error interno del servidor", e));
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Mascota mascota = service.getMascotaById(id);
            
            if (mascota != null && mascota.getFotos_mascota() != null) {
                // Eliminar imágenes asociadas
                for (String fotoUrl : mascota.getFotos_mascota()) {
                    try {
                        Path path = Paths.get("src/main/resources" + fotoUrl);
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        System.err.println("Error al eliminar la imagen: " + e.getMessage());
                    }
                }
            }
            
            service.deleteMascota(id);
            ctx.status(204);
        } catch (NumberFormatException e) {
            ctx.status(400).json(errorResponse("ID inválido", e));
        } catch (Exception e) {
            ctx.status(500).json(errorResponse("Error al eliminar mascota", e));
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

    private static Map<String, String> errorResponse(String message, Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
        response.put("details", e != null ? e.getMessage() : null);
        return response;
    }
}