package org.petlink.controller;

import java.util.List;

import org.petlink.model.Publicacion;
import org.petlink.service.PublicacionService;

import io.javalin.http.Context;

public class PublicacionController {

    private final PublicacionService service = new PublicacionService();

    public void getAll(Context ctx) {
        try {
            List<Publicacion> list = service.getAll();
            ctx.json(list);
        } catch (Exception e) {
            ctx.status(500).result("Error: " + e.getMessage());
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Publicacion p = service.getById(id);
            if (p != null) {
                ctx.json(p);
            } else {
                ctx.status(404).result("No encontrada");
            }
        } catch (Exception e) {
            ctx.status(400).result("ID inválido");
        }
    }

    public void create(Context ctx) {
        try {
            Publicacion p = ctx.bodyAsClass(Publicacion.class);
            service.create(p);
            ctx.status(201).result("Publicación creada");
        } catch (Exception e) {
            ctx.status(400).result("Error: " + e.getMessage());
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Publicacion p = ctx.bodyAsClass(Publicacion.class);
            service.update(id, p);
            ctx.result("Actualizada correctamente");
        } catch (Exception e) {
            ctx.status(400).result("Error: " + e.getMessage());
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

    public void vincularSolicitudAdopcion(Context ctx) {
    try {
        int idSolicitud = Integer.parseInt(ctx.pathParam("idSolicitud"));
        int idPublicacion = Integer.parseInt(ctx.pathParam("idPublicacion"));

        service.vincularSolicitudConPublicacion(idSolicitud, idPublicacion);

        ctx.status(201).result("✅ Solicitud vinculada con la publicación");
    } catch (Exception e) {
        ctx.status(400).result("❌ Error al vincular: " + e.getMessage());
    }
    }

}
