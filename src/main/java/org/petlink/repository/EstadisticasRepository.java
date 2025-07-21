package org.petlink.repository;

import org.petlink.model.EstadisticasMascotas;
import org.petlink.config.DatabaseConfig;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class EstadisticasRepository {

    public EstadisticasMascotas obtenerEstadisticas() throws Exception {  // Cambiado a throws Exception
        EstadisticasMascotas estadisticas = new EstadisticasMascotas();
        
        try (Connection conn = DatabaseConfig.getConnection()) {
            // Mascotas adoptadas esta semana
            String sql1 = "SELECT COUNT(*) AS mascotas_adoptadas FROM publicacion p " +
                         "JOIN solicitudadopcion_publicacion sp ON p.id_publicacion = sp.id_publicacion " +
                         "JOIN solicitud_adopcion sa ON sp.id_solicitudAdopcion = sa.id_solicitudAdopcion " +
                         "WHERE sa.estado_solicitudAdopcion = 'Aprobada' " +
                         "AND p.fecha_publicacion BETWEEN DATE_SUB(CURDATE(), INTERVAL 7 DAY) AND CURDATE()";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql1);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    estadisticas.setMascotasAdoptadasSemana(rs.getInt("mascotas_adoptadas"));
                }
            }

            // Mascotas más adoptadas por especie
            String sql2 = "SELECT e.nombre_especie, COUNT(*) AS total_adopciones FROM mascota m " +
                         "JOIN especie e ON m.codigo_especie = e.id_especie " +
                         "JOIN solicitud_cedente sc ON m.id_solicitudCedente = sc.id_solicitudCedente " +
                         "JOIN publicacion p ON sc.id_solicitudCedente = p.codigo_solicitudCedente " +
                         "JOIN solicitudadopcion_publicacion sp ON p.id_publicacion = sp.id_publicacion " +
                         "JOIN solicitud_adopcion sa ON sp.id_solicitudAdopcion = sa.id_solicitudAdopcion " +
                         "WHERE sa.estado_solicitudAdopcion = 'Aprobada' " +
                         "GROUP BY e.nombre_especie " +
                         "ORDER BY total_adopciones DESC";
            
            Map<String, Integer> mascotasPopulares = new HashMap<>();
            try (PreparedStatement stmt = conn.prepareStatement(sql2);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    mascotasPopulares.put(rs.getString("nombre_especie"), rs.getInt("total_adopciones"));
                }
            }
            estadisticas.setMascotasMasAdoptadas(mascotasPopulares);

            // Mascotas agregadas esta semana
            String sql3 = "SELECT COUNT(*) AS mascotas_agregadas FROM mascota m " +
                         "JOIN solicitud_cedente sc ON m.id_solicitudCedente = sc.id_solicitudCedente " +
                         "WHERE sc.fecha_solicitudCedente BETWEEN DATE_SUB(CURDATE(), INTERVAL 7 DAY) AND CURDATE()";
            
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