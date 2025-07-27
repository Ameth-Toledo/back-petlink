package org.petlink.service;

import org.petlink.model.Mascota;
import org.petlink.repository.MascotaRepository;

import java.sql.SQLException;
import java.util.List;

public class MascotaService {
    private final MascotaRepository repository = new MascotaRepository();

    public List<Mascota> getAllMascotas() throws SQLException {
        return repository.findAll();
    }

    public Mascota getMascotaById(int id) throws SQLException {
        return repository.findById(id);
    }

    public Mascota createMascota(Mascota mascota) throws SQLException, IllegalArgumentException {
        validateMascota(mascota);
        int id = repository.save(mascota);
        mascota.setId(id);
        return mascota;
    }

    public Mascota updateMascota(Mascota mascota) throws SQLException, IllegalArgumentException {
        validateMascota(mascota);
        if (mascota.getId() <= 0) {
            throw new IllegalArgumentException("ID de mascota inválido");
        }
        repository.update(mascota);
        return mascota;
    }

    public void deleteMascota(int id) throws SQLException {
        repository.delete(id);
    }

    private void validateMascota(Mascota mascota) throws IllegalArgumentException {
        if (mascota.getNombre() == null || mascota.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la mascota es requerido");
        }
        
        if (mascota.getEspecie() == null || mascota.getEspecie().trim().isEmpty()) {
            throw new IllegalArgumentException("La especie es requerida");
        }
        
        if (!mascota.getSexo().equalsIgnoreCase("macho") && !mascota.getSexo().equalsIgnoreCase("hembra")) {
            throw new IllegalArgumentException("El sexo debe ser 'macho' o 'hembra'");
        }
        
        if (mascota.getPeso() <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que cero");
        }
        
        if (mascota.getTamaño() == null || mascota.getTamaño().trim().isEmpty()) {
            throw new IllegalArgumentException("El tamaño es requerido");
        }
        
        if (!mascota.getEsterilizado().equalsIgnoreCase("sí") && !mascota.getEsterilizado().equalsIgnoreCase("no")) {
            throw new IllegalArgumentException("Esterilizado debe ser 'sí' o 'no'");
        }
        
        if (!mascota.getDiscapacitado().equalsIgnoreCase("sí") && !mascota.getDiscapacitado().equalsIgnoreCase("no")) {
            throw new IllegalArgumentException("Discapacitado debe ser 'sí' o 'no'");
        }
        
        if (!mascota.getDesparasitado().equalsIgnoreCase("sí") && !mascota.getDesparasitado().equalsIgnoreCase("no")) {
            throw new IllegalArgumentException("Desparasitado debe ser 'sí' o 'no'");
        }
        
        if (!mascota.getVacunas().equalsIgnoreCase("Completas") && 
            !mascota.getVacunas().equalsIgnoreCase("Incompletas") && 
            !mascota.getVacunas().equalsIgnoreCase("Ninguna")) {
            throw new IllegalArgumentException("Vacunas debe ser 'Completas', 'Incompletas' o 'Ninguna'");
        }
        
        if (mascota.getDescripcion() == null || mascota.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción es requerida");
        }
        
        if (mascota.getFotos_mascota() == null || mascota.getFotos_mascota().size() < 3) {
            throw new IllegalArgumentException("Se requieren al menos 3 fotos de la mascota");
        }
        
        for (String foto : mascota.getFotos_mascota()) {
            if (!foto.matches("^/uploads/mascotas/[a-zA-Z0-9_\\-]+\\.(jpg|jpeg|png)$")) {
                throw new IllegalArgumentException("Formato de ruta de imagen inválido: " + foto);
            }
        }
        
        if (mascota.getIdCedente() <= 0) {
            throw new IllegalArgumentException("ID del cedente es inválido");
        }
    }
}