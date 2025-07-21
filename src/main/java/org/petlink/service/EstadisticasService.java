package org.petlink.service;

import org.petlink.model.EstadisticasMascotas;
import org.petlink.repository.EstadisticasRepository;

public class EstadisticasService {
    private final EstadisticasRepository repository = new EstadisticasRepository();

    public EstadisticasMascotas obtenerEstadisticasMascotas() {
        try {
            return repository.obtenerEstadisticas();
        } catch (Exception e) { 
            e.printStackTrace();
            EstadisticasMascotas estadisticas = new EstadisticasMascotas();
            estadisticas.setMascotasAdoptadasSemana(0);
            estadisticas.setMascotasAgregadasSemana(0);
            return estadisticas;
        }
    }
}