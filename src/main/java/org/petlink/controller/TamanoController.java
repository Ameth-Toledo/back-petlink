package org.petlink.controller;

import io.javalin.http.Context;
import org.petlink.model.Tamano;
import org.petlink.service.TamanoService;

import java.util.List;

public class TamanoController {

    private final TamanoService service = new TamanoService();

    public void getAll(Context ctx) {
        try {
            List<Tamano> list = service.getAll();
            ctx.json(list);
        } catch (Exception e) {
            ctx.status(500).result("Error: " + e.getMessage());
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Tamano t = service.getById(id);
            if (t != null) {
                ctx.json(t);
            } else {
                ctx.status(404).result("No encontrado");
            }
        } catch (Exception e) {
            ctx.status(400).result("ID inválido");
        }
    }

    public void create(Context ctx) {
        try {
            Tamano t = ctx.bodyAsClass(Tamano.class);
            service.create(t);
            ctx.status(201).result("Tamaño creado");
        } catch (Exception e) {
            ctx.status(400).result("Error: " + e.getMessage());
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Tamano t = ctx.bodyAsClass(Tamano.class);
            service.update(id, t);
            ctx.result("Tamaño actualizado");
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
