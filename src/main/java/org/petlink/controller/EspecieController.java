package org.petlink.controller;

import io.javalin.http.Context;
import org.petlink.model.Especie;
import org.petlink.service.EspecieService;

import java.util.List;

public class EspecieController {

    private final EspecieService service = new EspecieService();

    public void getAll(Context ctx) {
        try {
            List<Especie> list = service.getAll();
            ctx.json(list);
        } catch (Exception e) {
            ctx.status(500).result("Error: " + e.getMessage());
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Especie e = service.getById(id);
            if (e != null) {
                ctx.json(e);
            } else {
                ctx.status(404).result("No encontrada");
            }
        } catch (Exception e) {
            ctx.status(400).result("ID inválido");
        }
    }

    public void create(Context ctx) {
        try {
            Especie e = ctx.bodyAsClass(Especie.class);
            service.create(e);
            ctx.status(201).result("Especie registrada");
        } catch (Exception e) {
            ctx.status(400).result("Error: " + e.getMessage());
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Especie e = ctx.bodyAsClass(Especie.class);
            service.update(id, e);
            ctx.result("Especie actualizada");
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
