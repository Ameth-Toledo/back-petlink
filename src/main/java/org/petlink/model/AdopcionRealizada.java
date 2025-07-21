package org.petlink.model;

import java.sql.Date;

public class AdopcionRealizada {
    private int id_adopcionRealizada;
    private Date fecha_adopcion;
    private int codigo_usuario;
    private int codigo_mascota;

    public int getId_adopcionRealizada() {
        return id_adopcionRealizada;
    }

    public void setId_adopcionRealizada(int id_adopcionRealizada) {
        this.id_adopcionRealizada = id_adopcionRealizada;
    }

    public Date getFecha_adopcion() {
        return fecha_adopcion;
    }

    public void setFecha_adopcion(Date fecha_adopcion) {
        this.fecha_adopcion = fecha_adopcion;
    }

    public int getCodigo_usuario() {
        return codigo_usuario;
    }

    public void setCodigo_usuario(int codigo_usuario) {
        this.codigo_usuario = codigo_usuario;
    }

    public int getCodigo_mascota() {
        return codigo_mascota;
    }

    public void setCodigo_mascota(int codigo_mascota) {
        this.codigo_mascota = codigo_mascota;
    }
}
