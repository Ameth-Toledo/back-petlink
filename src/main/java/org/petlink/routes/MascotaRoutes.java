package org.petlink.routes;

import io.javalin.Javalin;
import org.petlink.controller.MascotaController;

public class MascotaRoutes {

    public static void configure(Javalin app) {
        MascotaController controller = new MascotaController();

        app.get("/mascotas", controller::getAll);
        app.get("/mascotas/{id}", controller::getById);
        app.post("/mascotas", controller::create);
        app.put("/mascotas/{id}", controller::update);
        app.delete("/mascotas/{id}", controller::delete);
    }
}
