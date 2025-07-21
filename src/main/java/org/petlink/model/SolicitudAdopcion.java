package org.petlink.model;

import java.sql.Date;

public class SolicitudAdopcion {

    private int id_solicitudAdopcion;
    private String ocupacion_usuario;
    private String tipo_vivienda;
    private String mascotas_previas;
    private String estado_vivienda;
    private String permisopara_mascotas;
    private int numero_personas;
    private String niños_casa;
    private String experiencia_mascotas;
    private Date fecha_solicitudAdopcion;
    private String foto_vivienda;
    private String estado_solicitudAdopcion;

    private int codigo_usuario;

    public int getId_solicitudAdopcion() {
        return id_solicitudAdopcion;
    }

    public String getOcupacion_usuario() {
        return ocupacion_usuario;
    }

    public String getTipo_vivienda() {
        return tipo_vivienda;
    }

    public String getMascotas_previas() {
        return mascotas_previas;
    }

    public String getEstado_vivienda() {
        return estado_vivienda;
    }

    public String getPermisopara_mascotas() {
        return permisopara_mascotas;
    }

    public int getNumero_personas() {
        return numero_personas;
    }

    public String getNiños_casa() {
        return niños_casa;
    }

    public String getExperiencia_mascotas() {
        return experiencia_mascotas;
    }

    public Date getFecha_solicitudAdopcion() {
        return fecha_solicitudAdopcion;
    }

    public String getFoto_vivienda() {
        return foto_vivienda;
    }

    public String getEstado_solicitudAdopcion() {
        return estado_solicitudAdopcion;
    }

    public int getCodigo_usuario() {
        return codigo_usuario;
    }

    // Setters
    public void setId_solicitudAdopcion(int id_solicitudAdopcion) {
        this.id_solicitudAdopcion = id_solicitudAdopcion;
    }

    public void setOcupacion_usuario(String ocupacion_usuario) {
        this.ocupacion_usuario = ocupacion_usuario;
    }

    public void setTipo_vivienda(String tipo_vivienda) {
        this.tipo_vivienda = tipo_vivienda;
    }

    public void setMascotas_previas(String mascotas_previas) {
        this.mascotas_previas = mascotas_previas;
    }

    public void setEstado_vivienda(String estado_vivienda) {
        this.estado_vivienda = estado_vivienda;
    }

    public void setPermisopara_mascotas(String permisopara_mascotas) {
        this.permisopara_mascotas = permisopara_mascotas;
    }

    public void setNumero_personas(int numero_personas) {
        this.numero_personas = numero_personas;
    }

    public void setNiños_casa(String niños_casa) {
        this.niños_casa = niños_casa;
    }

    public void setExperiencia_mascotas(String experiencia_mascotas) {
        this.experiencia_mascotas = experiencia_mascotas;
    }

    public void setFecha_solicitudAdopcion(Date fecha_solicitudAdopcion) {
        this.fecha_solicitudAdopcion = fecha_solicitudAdopcion;
    }

    public void setFoto_vivienda(String foto_vivienda) {
        this.foto_vivienda = foto_vivienda;
    }

    public void setEstado_solicitudAdopcion(String estado_solicitudAdopcion) {
        this.estado_solicitudAdopcion = estado_solicitudAdopcion;
    }

    public void setCodigo_usuario(int codigo_usuario) {
        this.codigo_usuario = codigo_usuario;
    }
}
