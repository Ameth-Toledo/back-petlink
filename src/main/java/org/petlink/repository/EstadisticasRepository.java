package org.petlink.repository;

import org.petlink.model.EstadisticasMascotas;
import org.petlink.config.DatabaseConfig;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class EstadisticasRepository {

    public EstadisticasMascotas obtenerEstadisticas() throws Exception {
        EstadisticasMascotas estadisticas = new EstadisticasMascotas();
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            // 1. Mascotas adoptadas esta semana (según campo 'estado' en tabla mascota)
            String sql1 = "SELECT COUNT(*) AS mascotas_adoptadas FROM mascota " +
                         "WHERE estado = 'adoptado' " +
                         "AND fecha_registro BETWEEN DATE_SUB(CURDATE(), INTERVAL 7 DAY) AND CURDATE()";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql1);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    estadisticas.setMascotasAdoptadasSemana(rs.getInt("mascotas_adoptadas"));
                }
            }

            // 2. Mascotas más adoptadas por especie (usando campo 'especie' en tabla mascota)
            String sql2 = "SELECT especie, COUNT(*) AS total_adopciones FROM mascota " +
                         "WHERE estado = 'adoptado' " +
                         "GROUP BY especie " +
                         "ORDER BY total_adopciones DESC";
            
            Map<String, Integer> mascotasPopulares = new HashMap<>();
            try (PreparedStatement stmt = conn.prepareStatement(sql2);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    mascotasPopulares.put(rs.getString("especie"), rs.getInt("total_adopciones"));
                }
            }
            estadisticas.setMascotasMasAdoptadas(mascotasPopulares);

            // 3. Mascotas agregadas esta semana (todas las mascotas registradas)
            String sql3 = "SELECT COUNT(*) AS mascotas_agregadas FROM mascota " +
                         "WHERE fecha_registro BETWEEN DATE_SUB(CURDATE(), INTERVAL 7 DAY) AND CURDATE()";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql3);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    estadisticas.setMascotasAgregadasSemana(rs.getInt("mascotas_agregadas"));
                }
            }
        }
        
        return estadisticas;
    }
}