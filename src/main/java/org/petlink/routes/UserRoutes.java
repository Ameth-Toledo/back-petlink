package org.petlink.routes;

import io.javalin.Javalin;
import org.petlink.controller.UserController;

public class UserRoutes {
    public static void configure(Javalin app) {
        UserController controller = new UserController();

        app.get("/usuarios", controller::getAll);
        app.get("/usuarios/{id}", controller::getById);
        app.post("/usuarios", controller::create);
        app.put("/usuarios/{id}", controller::update);
        app.delete("/usuarios/{id}", controller::delete);
        app.post("/usuarios/login", controller::login);
    }
}
