package org.petlink.controller;

import io.javalin.http.Context;
import org.petlink.model.MascotaVacuna;
import org.petlink.service.MascotaVacunaService;

import java.util.List;

public class MascotaVacunaController {

    private final MascotaVacunaService service = new MascotaVacunaService();

    public void getAll(Context ctx) {
        try {
            List<MascotaVacuna> list = service.getAll();
            ctx.json(list);
        } catch (Exception e) {
            ctx.status(500).result("Error: " + e.getMessage());
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            MascotaVacuna mv = service.getById(id);
            if (mv != null) {
                ctx.json(mv);
            } else {
                ctx.status(404).result("No encontrado");
            }
        } catch (Exception e) {
            ctx.status(400).result("ID inválido");
        }
    }

    public void create(Context ctx) {
        try {
            MascotaVacuna mv = ctx.bodyAsClass(MascotaVacuna.class);
            service.create(mv);
            ctx.status(201).result("Registrado correctamente");
        } catch (Exception e) {
            ctx.status(400).result("Error: " + e.getMessage());
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            MascotaVacuna mv = ctx.bodyAsClass(MascotaVacuna.class);
            service.update(id, mv);
            ctx.result("Actualizado correctamente");
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
}
