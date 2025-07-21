package org.petlink.controller;

import io.javalin.http.Context;
import org.petlink.model.AdopcionRealizada;
import org.petlink.service.AdopcionRealizadaService;

import java.util.List;

public class AdopcionRealizadaController {

    private final AdopcionRealizadaService service = new AdopcionRealizadaService();

    public void getAll(Context ctx) {
        try {
            List<AdopcionRealizada> list = service.getAll();
            ctx.json(list);
        } catch (Exception e) {
            ctx.status(500).result("Error: " + e.getMessage());
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            AdopcionRealizada a = service.getById(id);
            if (a != null) {
                ctx.json(a);
            } else {
                ctx.status(404).result("No encontrada");
            }
        } catch (Exception e) {
            ctx.status(400).result("ID inválido");
        }
    }

    public void create(Context ctx) {
        try {
            AdopcionRealizada a = ctx.bodyAsClass(AdopcionRealizada.class);
            service.create(a);
            ctx.status(201).result("Adopción registrada");
        } catch (Exception e) {
            ctx.status(400).result("Error: " + e.getMessage());
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            AdopcionRealizada a = ctx.bodyAsClass(AdopcionRealizada.class);
            service.update(id, a);
            ctx.result("Adopción actualizada");
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
