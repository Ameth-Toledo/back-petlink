package org.petlink.model;

import java.sql.Date;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SolicitudCedente {
    private int id_solicitudCedente;
    private String estado_solicitudCedente;
    private Date fecha_solicitudCedente;
    private List<String> fotos_mascotas;
    private int codigo_usuario;

    public int getId_solicitudCedente() {
        return id_solicitudCedente;
    }

    public void setId_solicitudCedente(int id_solicitudCedente) {
        this.id_solicitudCedente = id_solicitudCedente;
    }

    public String getEstado_solicitudCedente() {
        return estado_solicitudCedente;
    }

    public void setEstado_solicitudCedente(String estado_solicitudCedente) {
        this.estado_solicitudCedente = estado_solicitudCedente;
    }

    public Date getFecha_solicitudCedente() {
        return fecha_solicitudCedente;
    }

    public void setFecha_solicitudCedente(Date fecha_solicitudCedente) {
        this.fecha_solicitudCedente = fecha_solicitudCedente;
    }

    public List<String> getFotos_mascotas() {
        return fotos_mascotas;
    }

    public void setFotos_mascotas(List<String> fotos_mascotas) {
        this.fotos_mascotas = fotos_mascotas;
    }

    public int getCodigo_usuario() {
        return codigo_usuario;
    }

    public void setCodigo_usuario(int codigo_usuario) {
        this.codigo_usuario = codigo_usuario;
    }

    public String getFotos_mascotasAsJson() {
        try {
            return new ObjectMapper().writeValueAsString(fotos_mascotas);
        } catch (Exception e) {
            return "[]";
        }
    }

    public void setFotos_mascotasFromJson(String json) {
        try {
            this.fotos_mascotas = new ObjectMapper().readValue(json, new TypeReference<List<String>>(){});
        } catch (Exception e) {
            this.fotos_mascotas = new java.util.ArrayList<>();
        }
    }
}