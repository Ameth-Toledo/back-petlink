package org.petlink.routes;

import io.javalin.Javalin;
import org.petlink.controller.VacunaController;

public class VacunaRoutes {

    public static void configure(Javalin app) {
        VacunaController controller = new VacunaController();

        app.get("/vacunas", controller::getAll);
        app.get("/vacunas/{id}", controller::getById);
        app.post("/vacunas", controller::create);
        app.put("/vacunas/{id}", controller::update);
        app.delete("/vacunas/{id}", controller::delete);
    }
}
