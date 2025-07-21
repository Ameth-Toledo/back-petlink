package org.petlink.routes;

import io.javalin.Javalin;
import org.petlink.controller.AdopcionRealizadaController;

public class AdopcionRealizadaRoutes {

    public static void configure(Javalin app) {
        AdopcionRealizadaController controller = new AdopcionRealizadaController();

        app.get("/adopciones", controller::getAll);
        app.get("/adopciones/{id}", controller::getById);
        app.post("/adopciones", controller::create);
        app.put("/adopciones/{id}", controller::update);
        app.delete("/adopciones/{id}", controller::delete);
    }
}
