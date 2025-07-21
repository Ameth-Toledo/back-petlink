package org.petlink.model;

import java.util.Map;

public class EstadisticasMascotas {
    private int mascotasAdoptadasSemana;
    private int mascotasAgregadasSemana;
    private Map<String, Integer> mascotasMasAdoptadas;

    public int getMascotasAdoptadasSemana() {
        return mascotasAdoptadasSemana;
    }

    public void setMascotasAdoptadasSemana(int mascotasAdoptadasSemana) {
        this.mascotasAdoptadasSemana = mascotasAdoptadasSemana;
    }

    public int getMascotasAgregadasSemana() {
        return mascotasAgregadasSemana;
    }

    public void setMascotasAgregadasSemana(int mascotasAgregadasSemana) {
        this.mascotasAgregadasSemana = mascotasAgregadasSemana;
    }

    public Map<String, Integer> getMascotasMasAdoptadas() {
        return mascotasMasAdoptadas;
    }

    public void setMascotasMasAdoptadas(Map<String, Integer> mascotasMasAdoptadas) {
        this.mascotasMasAdoptadas = mascotasMasAdoptadas;
    }
}