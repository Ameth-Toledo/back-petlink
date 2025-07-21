package org.petlink.controller;

import io.javalin.http.Context;
import org.petlink.model.Mascota;
import org.petlink.service.MascotaService;

import java.util.List;

public class MascotaController {

    private final MascotaService service = new MascotaService();

    public void getAll(Context ctx) {
        try {
            List<Mascota> mascotas = service.getAll();
            ctx.json(mascotas);
        } catch (Exception e) {
            ctx.status(500).result("Error al obtener mascotas: " + e.getMessage());
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Mascota mascota = service.getById(id);
            if (mascota != null) {
                ctx.json(mascota);
            } else {
                ctx.status(404).result("Mascota no encontrada");
            }
        } catch (Exception e) {
            ctx.status(400).result("ID inválido o error: " + e.getMessage());
        }
    }

    public void create(Context ctx) {
        try {
            Mascota m = ctx.bodyAsClass(Mascota.class);
            service.create(m);
            ctx.status(201).result("Mascota registrada correctamente");
        } catch (Exception e) {
            ctx.status(400).result("Error al registrar mascota: " + e.getMessage());
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Mascota m = ctx.bodyAsClass(Mascota.class);
            service.update(id, m);
            ctx.status(200).result("Mascota actualizada correctamente");
        } catch (Exception e) {
            ctx.status(400).result("Error al actualizar mascota: " + e.getMessage());
        }
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            service.delete(id);
            ctx.status(204);
        } catch (Exception e) {
            ctx.status(400).result("Error al eliminar mascota: " + e.getMessage());
        }
    }
}
