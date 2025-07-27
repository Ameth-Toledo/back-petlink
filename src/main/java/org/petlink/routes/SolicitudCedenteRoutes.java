package org.petlink.routes;

import io.javalin.Javalin;
import org.petlink.controller.SolicitudCedenteController;

public class SolicitudCedenteRoutes {

    public static void configure(Javalin app) {
        SolicitudCedenteController controller = new SolicitudCedenteController();

        app.get("/solicitudes-cedente", controller::getAll);
        app.get("/solicitudes-cedente/{id}", controller::getById);
        app.post("/solicitudes-cedente", controller::create);
        app.put("/solicitudes-cedente/{id}", controller::update);
        app.delete("/solicitudes-cedente/{id}", controller::delete);
    }
}