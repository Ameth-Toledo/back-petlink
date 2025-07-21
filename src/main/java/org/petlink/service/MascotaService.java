package org.petlink.service;

import org.petlink.model.Mascota;
import org.petlink.repository.MascotaRepository;

import java.util.List;

public class MascotaService {

    private final MascotaRepository repository = new MascotaRepository();

    public List<Mascota> getAll() throws Exception {
        return repository.findAll();
    }

    public Mascota getById(int id) throws Exception {
        return repository.findById(id);
    }

    public void create(Mascota m) throws Exception {
        validate(m);
        repository.save(m);
    }

    public void update(int id, Mascota m) throws Exception {
        validate(m);
        repository.update(id, m);
    }

    public void delete(int id) throws Exception {
        repository.delete(id);
    }

    private void validate(Mascota m) throws Exception {
        if (m.getNombre_mascotas() == null || m.getNombre_mascotas().isEmpty())
            throw new Exception("El nombre de la mascota es obligatorio.");

        if (m.getSexo() == null || (!m.getSexo().equalsIgnoreCase("macho") && !m.getSexo().equalsIgnoreCase("hembra")))
            throw new Exception("El sexo debe ser 'macho' o 'hembra'.");

        if (m.getPeso() <= 0)
            throw new Exception("El peso debe ser mayor que 0.");

        if (m.getRaza() == null || m.getRaza().isEmpty())
            throw new Exception("La raza es obligatoria.");

        if (!m.getEsterilizado().equalsIgnoreCase("sí") && !m.getEsterilizado().equalsIgnoreCase("no"))
            throw new Exception("Esterilizado debe ser 'sí' o 'no'.");

        if (!m.getDesparasitado().equalsIgnoreCase("sí") && !m.getDesparasitado().equalsIgnoreCase("no"))
            throw new Exception("Desparasitado debe ser 'sí' o 'no'.");

        if (m.getDescripcion() == null || m.getDescripcion().isEmpty())
            throw new Exception("La descripción es obligatoria.");

        if (m.getCodigo_especie() <= 0 || m.getCodigo_tamaño() <= 0 || m.getCodigo_vacunas() <= 0 || m.getId_Cedente() <= 0)
            throw new Exception("Las claves foráneas deben ser mayores que 0.");
    }
}
