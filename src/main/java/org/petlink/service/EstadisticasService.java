package org.petlink.service;

import org.petlink.model.EstadisticasMascotas;
import org.petlink.repository.EstadisticasRepository;
import java.util.HashMap;  // Importación faltante

public class EstadisticasService {
    private final EstadisticasRepository repository = new EstadisticasRepository();

    public EstadisticasMascotas obtenerEstadisticasMascotas() {
        try {
            EstadisticasMascotas stats = repository.obtenerEstadisticas();
            // Asegurarse que nunca devuelva null para mascotasMasAdoptadas
            if (stats.getMascotasMasAdoptadas() == null) {
                stats.setMascotasMasAdoptadas(new HashMap<>());
            }
            return stats;
        } catch (Exception e) { 
            e.printStackTrace();
            EstadisticasMascotas estadisticas = new EstadisticasMascotas();
            estadisticas.setMascotasAdoptadasSemana(0);
            estadisticas.setMascotasAgregadasSemana(0);
            estadisticas.setMascotasMasAdoptadas(new HashMap<>());
            return estadisticas;
        }
    }
}