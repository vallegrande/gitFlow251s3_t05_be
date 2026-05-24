package Agropacayales.valleGrande.service.impl;

import Agropacayales.valleGrande.model.Parcela;
import Agropacayales.valleGrande.repository.ParcelaRepository;
import Agropacayales.valleGrande.service.ParcelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ParcelaServiceImpl implements ParcelaService {

    @Autowired
    private ParcelaRepository parcelaRepository;

    @Override
    public List<Parcela> listarTodos() {
        return parcelaRepository.findAll();
    }

    @Override
    public Optional<Parcela> listarPorId(Long id) {
        return parcelaRepository.findById(id);
    }

    @Override
    public List<Parcela> listarPorEstado(Boolean estado) {
        return parcelaRepository.findByEstado(estado);
    }

    @Override
    public Parcela crear(Parcela parcela) {
        if (parcelaRepository.existsByNombre(parcela.getNombre())) {
            throw new RuntimeException("Ya existe una parcela con el nombre: " + parcela.getNombre());
        }
        LocalDateTime now = LocalDateTime.now();
        parcela.setEstado(true);
        parcela.setEnUso(false); // Inicializar explícitamente por seguridad
        parcela.setCreatedAt(now);
        parcela.setUpdatedAt(null);
        parcela.setDeletedAt(null);
        parcela.setRestoredAt(null);
        return parcelaRepository.save(parcela);
    }

    @Override
    public Parcela editar(Long id, Parcela datos) {
        Optional<Parcela> existente = parcelaRepository.findById(id);
        if (existente.isPresent()) {
            Parcela parcela = existente.get();
            
            // Si el nombre cambió, verificar que no exista ya otro con el mismo nombre
            if (!parcela.getNombre().equals(datos.getNombre()) && parcelaRepository.existsByNombre(datos.getNombre())) {
                throw new RuntimeException("Ya existe otra parcela con el nombre: " + datos.getNombre());
            }

            parcela.setNombre(datos.getNombre());
            parcela.setUbicacion(datos.getUbicacion());
            parcela.setAreaHectareas(datos.getAreaHectareas());
            parcela.setTipoSuelo(datos.getTipoSuelo());
            parcela.setResponsable(datos.getResponsable());
            parcela.setEstadoRiego(datos.getEstadoRiego());
            parcela.setFechaUltimaSiembra(datos.getFechaUltimaSiembra());
            parcela.setProduccionEstimada(datos.getProduccionEstimada());
            parcela.setCultivoActual(datos.getCultivoActual());
            parcela.setObservaciones(datos.getObservaciones());
            parcela.setUpdatedAt(LocalDateTime.now());
            return parcelaRepository.save(parcela);
        }
        return null;
    }

    @Override
    public Parcela eliminar(Long id) {
        Optional<Parcela> existente = parcelaRepository.findById(id);
        if (existente.isPresent()) {
            Parcela parcela = existente.get();
            parcela.setEstado(false);
            parcela.setDeletedAt(LocalDateTime.now());
            return parcelaRepository.save(parcela);
        }
        return null;
    }

    @Override
    public Parcela restaurar(Long id) {
        Optional<Parcela> existente = parcelaRepository.findById(id);
        if (existente.isPresent()) {
            Parcela parcela = existente.get();
            parcela.setEstado(true);
            parcela.setRestoredAt(LocalDateTime.now());
            return parcelaRepository.save(parcela);
        }
        return null;
    }
}
