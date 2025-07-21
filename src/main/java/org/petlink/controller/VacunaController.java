package org.petlink.controller;

import io.javalin.http.Context;
import org.petlink.model.Vacuna;
import org.petlink.service.VacunaService;

import java.util.List;

public class VacunaController {

    private final VacunaService service = new VacunaService();

    public void getAll(Context ctx) {
        try {
            List<Vacuna> vacunas = service.getAll();
            ctx.json(vacunas);
        } catch (Exception e) {
            ctx.status(500).result("Error: " + e.getMessage());
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Vacuna v = service.getById(id);
            if (v != null) {
                ctx.json(v);
            } else {
                ctx.status(404).result("Vacuna no encontrada");
            }
        } catch (Exception e) {
            ctx.status(400).result("ID inválido");
        }
    }

    public void create(Context ctx) {
        try {
            Vacuna v = ctx.bodyAsClass(Vacuna.class);
            service.create(v);
            ctx.status(201).result("Vacuna registrada");
        } catch (Exception e) {
            ctx.status(400).result("Error: " + e.getMessage());
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Vacuna v = ctx.bodyAsClass(Vacuna.class);
            service.update(id, v);
            ctx.result("Vacuna actualizada");
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
