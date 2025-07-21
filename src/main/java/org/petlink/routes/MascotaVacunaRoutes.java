package org.petlink.routes;

import io.javalin.Javalin;
import org.petlink.controller.MascotaVacunaController;

public class MascotaVacunaRoutes {

    public static void configure(Javalin app) {
        MascotaVacunaController controller = new MascotaVacunaController();

        app.get("/mascotas-vacunas", controller::getAll);
        app.get("/mascotas-vacunas/{id}", controller::getById);
        app.post("/mascotas-vacunas", controller::create);
        app.put("/mascotas-vacunas/{id}", controller::update);
        app.delete("/mascotas-vacunas/{id}", controller::delete);
    }
}
