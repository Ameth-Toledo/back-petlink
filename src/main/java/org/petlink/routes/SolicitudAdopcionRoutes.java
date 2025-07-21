package org.petlink.routes;

import io.javalin.Javalin;
import org.petlink.controller.SolicitudAdopcionController;

public class SolicitudAdopcionRoutes {

    public static void configure(Javalin app) {
        SolicitudAdopcionController controller = new SolicitudAdopcionController();

        app.get("/solicitudes-adopcion", controller::getAll);
        app.get("/solicitudes-adopcion/{id}", controller::getById);
        app.post("/solicitudes-adopcion", controller::create);
        app.put("/solicitudes-adopcion/{id}", controller::update);
        app.delete("/solicitudes-adopcion/{id}", controller::delete);
    }
}
