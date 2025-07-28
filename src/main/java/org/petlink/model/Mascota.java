package org.petlink.model;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Mascota {
    private int id;
    private String nombre;
    private String especie;
    private String sexo;
    private float peso;
    private String tamaño;
    private String esterilizado;
    private String discapacitado;
    private String desparasitado;
    private String vacunas;
    private String descripcion;
    private List<String> fotos_mascota;
    private int idCedente;
    private String estado;
    
    public Mascota() {}

    public Mascota(String nombre, String especie, String sexo, float peso, String tamaño, 
                  String esterilizado, String discapacitado, String desparasitado, 
                  String vacunas, String descripcion, List<String> fotos_mascota, int idCedente, String estado) {
        this.nombre = nombre;
        this.especie = especie;
        this.sexo = sexo;
        this.peso = peso;
        this.tamaño = tamaño;
        this.esterilizado = esterilizado;
        this.discapacitado = discapacitado;
        this.desparasitado = desparasitado;
        this.vacunas = vacunas;
        this.descripcion = descripcion;
        this.fotos_mascota = fotos_mascota;
        this.idCedente = idCedente;
        this.estado = estado;
    }

    public String getFotosAsString() {
        if (fotos_mascota == null || fotos_mascota.isEmpty()) {
            return "";
        }
        return String.join(",", fotos_mascota);
    }

    public void setFotosFromString(String fotosString) {
        if (fotosString == null || fotosString.trim().isEmpty()) {
            this.fotos_mascota = null;
        } else {
            this.fotos_mascota = Arrays.stream(fotosString.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        }
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public String getEsterilizado() {
        return esterilizado;
    }

    public void setEsterilizado(String esterilizado) {
        this.esterilizado = esterilizado;
    }

    public String getDiscapacitado() {
        return discapacitado;
    }

    public void setDiscapacitado(String discapacitado) {
        this.discapacitado = discapacitado;
    }

    public String getDesparasitado() {
        return desparasitado;
    }

    public void setDesparasitado(String desparasitado) {
        this.desparasitado = desparasitado;
    }

    public String getVacunas() {
        return vacunas;
    }

    public void setVacunas(String vacunas) {
        this.vacunas = vacunas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getFotos_mascota() {
        return fotos_mascota;
    }

    public void setFotos_mascota(List<String> fotos_mascota) {
        this.fotos_mascota = fotos_mascota;
    }

    public int getIdCedente() {
        return idCedente;
    }

    public void setIdCedente(int idCedente) {
        this.idCedente = idCedente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}