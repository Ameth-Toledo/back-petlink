package org.petlink.model;

import java.sql.Date;

public class MascotaVacuna {
    private int id;
    private int codigo_mascota;
    private int codigo_vacuna;
    private Date fecha_aplicacion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigo_mascota() {
        return codigo_mascota;
    }

    public void setCodigo_mascota(int codigo_mascota) {
        this.codigo_mascota = codigo_mascota;
    }

    public int getCodigo_vacuna() {
        return codigo_vacuna;
    }

    public void setCodigo_vacuna(int codigo_vacuna) {
        this.codigo_vacuna = codigo_vacuna;
    }

    public Date getFecha_aplicacion() {
        return fecha_aplicacion;
    }

    public void setFecha_aplicacion(Date fecha_aplicacion) {
        this.fecha_aplicacion = fecha_aplicacion;
    }
}
