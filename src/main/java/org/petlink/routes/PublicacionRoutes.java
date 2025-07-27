package org.petlink.routes;

import org.petlink.controller.PublicacionController;

import io.javalin.Javalin;

public class PublicacionRoutes {

    public static void configure(Javalin app) {
        PublicacionController controller = new PublicacionController();

        app.get("/publicaciones", controller::getAll);
        app.get("/publicaciones/{id}", controller::getById);
        app.post("/publicaciones", controller::create);
        app.put("/publicaciones/{id}", controller::update);
        app.delete("/publicaciones/{id}", controller::delete);

        app.post("/publicaciones/vincular/solicitud/{idSolicitud}/publicacion/{idPublicacion}",
                 controller::vincularSolicitudAdopcion);
    }
}
