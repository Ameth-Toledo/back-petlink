package org.petlink.model;

import java.sql.Date;

public class SolicitudAdopcion {
    private String mascotaId;
    private String adoptanteId;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private int edad;
    private String correo;
    private String ocupacion;
    private int personasVivienda;
    private String hayNinos;
    private String permiteMascotas;
    private String tipoVivienda;
    private String tipoPropiedad;
    private String experiencia;
    private String historialMascotas;
    private String ineDocument; // Puede ser String (ruta) o byte[] para el archivo
    private String espacioMascota; // Puede ser String (ruta) o byte[] para el archivo
    private Date fechaSolicitud;
    private String estadoSolicitud;

    // Constructores
    public SolicitudAdopcion() {
    }

    public SolicitudAdopcion(String mascotaId, String adoptanteId, String nombre, String apellidoPaterno, 
                           String apellidoMaterno, int edad, String correo, String ocupacion, 
                           int personasVivienda, String hayNinos, String permiteMascotas, 
                           String tipoVivienda, String tipoPropiedad, String experiencia, 
                           String historialMascotas, String ineDocument, String espacioMascota) {
        this.mascotaId = mascotaId;
        this.adoptanteId = adoptanteId;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.edad = edad;
        this.correo = correo;
        this.ocupacion = ocupacion;
        this.personasVivienda = personasVivienda;
        this.hayNinos = hayNinos;
        this.permiteMascotas = permiteMascotas;
        this.tipoVivienda = tipoVivienda;
        this.tipoPropiedad = tipoPropiedad;
        this.experiencia = experiencia;
        this.historialMascotas = historialMascotas;
        this.ineDocument = ineDocument;
        this.espacioMascota = espacioMascota;
        this.fechaSolicitud = new Date(System.currentTimeMillis());
        this.estadoSolicitud = "pendiente"; // Valor por defecto
    }

    // Getters y Setters
    public String getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(String mascotaId) {
        this.mascotaId = mascotaId;
    }

    public String getAdoptanteId() {
        return adoptanteId;
    }

    public void setAdoptanteId(String adoptanteId) {
        this.adoptanteId = adoptanteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public int getPersonasVivienda() {
        return personasVivienda;
    }

    public void setPersonasVivienda(int personasVivienda) {
        this.personasVivienda = personasVivienda;
    }

    public String getHayNinos() {
        return hayNinos;
    }

    public void setHayNinos(String hayNinos) {
        this.hayNinos = hayNinos;
    }

    public String getPermiteMascotas() {
        return permiteMascotas;
    }

    public void setPermiteMascotas(String permiteMascotas) {
        this.permiteMascotas = permiteMascotas;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public String getTipoPropiedad() {
        return tipoPropiedad;
    }

    public void setTipoPropiedad(String tipoPropiedad) {
        this.tipoPropiedad = tipoPropiedad;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getHistorialMascotas() {
        return historialMascotas;
    }

    public void setHistorialMascotas(String historialMascotas) {
        this.historialMascotas = historialMascotas;
    }

    public String getIneDocument() {
        return ineDocument;
    }

    public void setIneDocument(String ineDocument) {
        this.ineDocument = ineDocument;
    }

    public String getEspacioMascota() {
        return espacioMascota;
    }

    public void setEspacioMascota(String espacioMascota) {
        this.espacioMascota = espacioMascota;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    @Override
    public String toString() {
        return "SolicitudAdopcion{" +
                "mascotaId='" + mascotaId + '\'' +
                ", adoptanteId='" + adoptanteId + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", edad=" + edad +
                ", correo='" + correo + '\'' +
                ", ocupacion='" + ocupacion + '\'' +
                ", personasVivienda=" + personasVivienda +
                ", hayNinos='" + hayNinos + '\'' +
                ", permiteMascotas='" + permiteMascotas + '\'' +
                ", tipoVivienda='" + tipoVivienda + '\'' +
                ", tipoPropiedad='" + tipoPropiedad + '\'' +
                ", experiencia='" + experiencia + '\'' +
                ", historialMascotas='" + historialMascotas + '\'' +
                ", ineDocument='" + ineDocument + '\'' +
                ", espacioMascota='" + espacioMascota + '\'' +
                ", fechaSolicitud=" + fechaSolicitud +
                ", estadoSolicitud='" + estadoSolicitud + '\'' +
                '}';
    }
}