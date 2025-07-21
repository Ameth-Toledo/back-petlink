package org.petlink.di;

import org.petlink.routes.UserRoutes;

public class AppModule {
    public static UserRoutes initUser() {
        return new UserRoutes(); 
    }
}
