package org.petlink.routes;

import io.javalin.Javalin;
import org.petlink.controller.EspecieController;

public class EspecieRoutes {

    public static void configure(Javalin app) {
        EspecieController controller = new EspecieController();

        app.get("/especies", controller::getAll);
        app.get("/especies/{id}", controller::getById);
        app.post("/especies", controller::create);
        app.put("/especies/{id}", controller::update);
        app.delete("/especies/{id}", controller::delete);
    }
}
