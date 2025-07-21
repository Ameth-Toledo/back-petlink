package org.petlink.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConfig {
    private static final Dotenv dotenv = Dotenv.load();

    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://" +
                dotenv.get("DB_HOST") + ":" +
                dotenv.get("DB_PORT") + "/" +
                dotenv.get("DB_NAME") +
                "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        return DriverManager.getConnection(url, user, password);
    }
}
