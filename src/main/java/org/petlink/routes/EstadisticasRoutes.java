package org.petlink.routes;

import io.javalin.Javalin;
import org.petlink.controller.EstadisticasController;

public class EstadisticasRoutes {

    public static void configure(Javalin app) {
        EstadisticasController controller = new EstadisticasController();

        app.get("/estadisticas/mascotas", controller::obtenerEstadisticasMascotas);
    }
}