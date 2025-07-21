package org.petlink.model;

import java.sql.Date;

public class Publicacion {
    private int id_publicacion;
    private String estado_publicacion;
    private Date fecha_publicacion;
    private int Codigo; // id de mascota
    private int codigo_solicitudCedente;
    private int codigo_usuario;

    public int getId_publicacion() {
        return id_publicacion;
    }

    public void setId_publicacion(int id_publicacion) {
        this.id_publicacion = id_publicacion;
    }

    public String getEstado_publicacion() {
        return estado_publicacion;
    }

    public void setEstado_publicacion(String estado_publicacion) {
        this.estado_publicacion = estado_publicacion;
    }

    public Date getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(Date fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int Codigo) {
        this.Codigo = Codigo;
    }

    public int getCodigo_solicitudCedente() {
        return codigo_solicitudCedente;
    }

    public void setCodigo_solicitudCedente(int codigo_solicitudCedente) {
        this.codigo_solicitudCedente = codigo_solicitudCedente;
    }

    public int getCodigo_usuario() {
        return codigo_usuario;
    }

    public void setCodigo_usuario(int codigo_usuario) {
        this.codigo_usuario = codigo_usuario;
    }
}
