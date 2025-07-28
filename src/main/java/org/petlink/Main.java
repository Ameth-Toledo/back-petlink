package org.petlink;

import org.petlink.di.AppModule;
import org.petlink.routes.AdopcionRealizadaRoutes;
import org.petlink.routes.EspecieRoutes;
import org.petlink.routes.MascotaRoutes;
import org.petlink.routes.MascotaVacunaRoutes;
import org.petlink.routes.PublicacionRoutes;
import org.petlink.routes.SolicitudAdopcionRoutes;
import org.petlink.routes.SolicitudCedenteRoutes;
import org.petlink.routes.TamanoRoutes;
import org.petlink.routes.UserRoutes;
import org.petlink.routes.VacunaRoutes;
import org.petlink.routes.EstadisticasRoutes;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/uploads"; 
                staticFiles.directory = "src/main/resources/uploads"; 
                staticFiles.location = Location.EXTERNAL; 
                staticFiles.precompress = false;
            });
            
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> it.anyHost());
            });
            
            config.http.maxRequestSize = 25_000_000; 
        }).start(7078);

        UserRoutes.configure(app);
        MascotaRoutes.configure(app); 
        SolicitudCedenteRoutes.configure(app);
        PublicacionRoutes.configure(app);
        AdopcionRealizadaRoutes.configure(app);
        VacunaRoutes.configure(app);
        MascotaVacunaRoutes.configure(app);
        EspecieRoutes.configure(app);
        TamanoRoutes.configure(app);
        SolicitudAdopcionRoutes.configure(app);
        EstadisticasRoutes.configure(app);

        AppModule.initUser();
    }
}