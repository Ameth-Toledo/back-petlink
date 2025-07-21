package org.petlink.controller;

import io.javalin.http.Context;
import org.petlink.model.EstadisticasMascotas;
import org.petlink.service.EstadisticasService;

public class EstadisticasController {
    private final EstadisticasService service = new EstadisticasService();

    public void obtenerEstadisticasMascotas(Context ctx) {
        EstadisticasMascotas estadisticas = service.obtenerEstadisticasMascotas();
        if (estadisticas != null) {
            ctx.json(estadisticas);
        } else {
            ctx.status(500).json("Error al obtener estadísticas");
        }
    }
}