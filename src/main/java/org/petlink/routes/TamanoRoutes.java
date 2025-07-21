package org.petlink.routes;

import io.javalin.Javalin;
import org.petlink.controller.TamanoController;

public class TamanoRoutes {

    public static void configure(Javalin app) {
        TamanoController controller = new TamanoController();

        app.get("/tamanos", controller::getAll);
        app.get("/tamanos/{id}", controller::getById);
        app.post("/tamanos", controller::create);
        app.put("/tamanos/{id}", controller::update);
        app.delete("/tamanos/{id}", controller::delete);
    }
}
