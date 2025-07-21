package org.petlink.model;

public class Mascota {
    private int id_mascotas;
    private String nombre_mascotas;
    private int codigo_especie;
    private String sexo;
    private float peso;
    private int codigo_tamaño;
    private String raza;
    private String esterilizado;
    private String desparasitado;
    private String discapacitado;
    private String enfermedades;
    private int codigo_vacunas;
    private String descripcion;
    private int id_Cedente;

    // Getters y Setters
    public int getId_mascotas() {
        return id_mascotas;
    }

    public void setId_mascotas(int id_mascotas) {
        this.id_mascotas = id_mascotas;
    }

    public String getNombre_mascotas() {
        return nombre_mascotas;
    }

    public void setNombre_mascotas(String nombre_mascotas) {
        this.nombre_mascotas = nombre_mascotas;
    }

    public int getCodigo_especie() {
        return codigo_especie;
    }

    public void setCodigo_especie(int codigo_especie) {
        this.codigo_especie = codigo_especie;
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

    public int getCodigo_tamaño() {
        return codigo_tamaño;
    }

    public void setCodigo_tamaño(int codigo_tamaño) {
        this.codigo_tamaño = codigo_tamaño;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getEsterilizado() {
        return esterilizado;
    }

    public void setEsterilizado(String esterilizado) {
        this.esterilizado = esterilizado;
    }

    public String getDesparasitado() {
        return desparasitado;
    }

    public void setDesparasitado(String desparasitado) {
        this.desparasitado = desparasitado;
    }

    public String getDiscapacitado() {
        return discapacitado;
    }

    public void setDiscapacitado(String discapacitado) {
        this.discapacitado = discapacitado;
    }

    public String getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(String enfermedades) {
        this.enfermedades = enfermedades;
    }

    public int getCodigo_vacunas() {
        return codigo_vacunas;
    }

    public void setCodigo_vacunas(int codigo_vacunas) {
        this.codigo_vacunas = codigo_vacunas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_Cedente() {
        return id_Cedente;
    }

    public void setId_Cedente(int id_Cedente) {
        this.id_Cedente = id_Cedente;
    }
}
