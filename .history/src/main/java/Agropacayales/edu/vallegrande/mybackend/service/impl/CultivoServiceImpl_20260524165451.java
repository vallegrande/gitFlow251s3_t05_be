package Agropacayales.edu.vallegrande.mybackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Agropacayales.valleGrande.dto.CultivoRequestDTO;
import Agropacayales.valleGrande.model.Cultivo;
import Agropacayales.valleGrande.model.Parcela;
import Agropacayales.valleGrande.repository.CultivoRepository;
import Agropacayales.valleGrande.repository.ParcelaRepository;
import Agropacayales.valleGrande.service.CultivoService; 

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CultivoServiceImpl implements CultivoService { 

    @Autowired
    private CultivoRepository repository;
    
    @Autowired
    private ParcelaRepository parcelaRepository;

    @Override
    public List<Cultivo> listarTodos() { 
        return repository.findAll(); 
    }

    @Override
    public Optional<Cultivo> listarPorId(Long id) { 
        return repository.findById(id); 
    }

    @Override
    public List<Cultivo> listarPorEstado(Boolean estado) {
        return repository.findByEstado(estado);
    }

    @Override
    public List<Cultivo> listarPorParcela(Long parcelaId) {
        return repository.findByParcelaIdParcela(parcelaId);
    }

    /**
     * TRANSACCIÓN FUNCIONAL: Crear siembra de cultivo
     * - Valida parcela disponible
     * - Calcula costos automáticamente
     * - Actualiza estado de parcela
     * - Calcula fechas y producción estimada
     */
    @Override
    @Transactional
    public Cultivo crearSiembra(CultivoRequestDTO request) {
        // 1. Validar parcela existe y está disponible
        Parcela parcela = parcelaRepository.findById(request.getParcelaId())
                .orElseThrow(() -> new RuntimeException("Parcela no encontrada"));

        if (parcela.getEnUso()) {
            throw new RuntimeException("La parcela ya está en uso");
        }

        // 2. Validar área no exceda la parcela
        if (request.getAreaSembrada().compareTo(parcela.getAreaHectareas()) > 0) {
            throw new RuntimeException("El área a sembrar excede el área de la parcela");
        }

        // 3. Crear cultivo con cálculos automáticos
        Cultivo cultivo = new Cultivo();
        cultivo.setParcela(parcela);
        cultivo.setNombre(request.getNombre());
        cultivo.setTipoCultivo(request.getTipoCultivo());
        cultivo.setAreaSembrada(request.getAreaSembrada());
        cultivo.setResponsable(request.getResponsable());
        cultivo.setFrecuenciaRiegoDias(request.getFrecuenciaRiegoDias());
        cultivo.setTemperaturaIdeal(request.getTemperaturaIdeal());
        cultivo.setRequiereSombra(request.getRequiereSombra());
        cultivo.setObservaciones(request.getObservaciones());

        // 4. CAMPOS CALCULABLES AUTOMÁTICOS
        cultivo.setFechaSiembra(LocalDate.now());
        cultivo.setCostoSiembra(calcularCostoSiembra(request.getTipoCultivo(), request.getAreaSembrada()));
        cultivo.setFechaCosechaEstimada(calcularFechaCosecha(request.getTipoCultivo()));
        cultivo.setProduccionEstimadaKg(calcularProduccionEstimada(request.getTipoCultivo(), request.getAreaSembrada()));

        // 5. Campos de auditoría
        cultivo.setEstado(true);
        cultivo.setCreatedAt(LocalDateTime.now());

        // 6. ACTUALIZAR PARCELA (lógica de negocio)
        parcela.setEnUso(true);
        parcela.setCultivoActual(request.getTipoCultivo());
        parcela.setFechaUltimaSiembra(LocalDate.now());
        parcela.setUpdatedAt(LocalDateTime.now());
        parcelaRepository.save(parcela);

        return repository.save(cultivo);
    }

    // LÓGICA DE NEGOCIO: Calcular costo por tipo de cultivo
    private BigDecimal calcularCostoSiembra(String tipoCultivo, BigDecimal area) {
        BigDecimal costoPorHa = switch (tipoCultivo.toUpperCase()) {
            case "CAFE" -> new BigDecimal("5000.00");
            case "CACAO" -> new BigDecimal("4500.00");
            case "MAIZ" -> new BigDecimal("2000.00");
            case "ARROZ" -> new BigDecimal("3000.00");
            case "PLATANO" -> new BigDecimal("3500.00");
            default -> new BigDecimal("3000.00");
        };
        return costoPorHa.multiply(area);
    }

    // LÓGICA DE NEGOCIO: Calcular fecha de cosecha estimada
    private LocalDate calcularFechaCosecha(String tipoCultivo) {
        int mesesCosecha = switch (tipoCultivo.toUpperCase()) {
            case "CAFE" -> 36; // 3 años
            case "CACAO" -> 24; // 2 años
            case "MAIZ" -> 4; // 4 meses
            case "ARROZ" -> 5; // 5 meses
            case "PLATANO" -> 12; // 1 año
            default -> 6; // 6 meses por defecto
        };
        return LocalDate.now().plusMonths(mesesCosecha);
    }

    // LÓGICA DE NEGOCIO: Calcular producción estimada
    private BigDecimal calcularProduccionEstimada(String tipoCultivo, BigDecimal area) {
        BigDecimal produccionPorHa = switch (tipoCultivo.toUpperCase()) {
            case "CAFE" -> new BigDecimal("1200.00"); // kg/ha
            case "CACAO" -> new BigDecimal("800.00");
            case "MAIZ" -> new BigDecimal("3000.00");
            case "ARROZ" -> new BigDecimal("4000.00");
            case "PLATANO" -> new BigDecimal("15000.00");
            default -> new BigDecimal("2000.00");
        };
        return produccionPorHa.multiply(area);
    }

    @Override
    public Cultivo crear(Cultivo cultivo) { 
        cultivo.setEstado(true);
        cultivo.setCreatedAt(LocalDateTime.now());
        cultivo.setUpdatedAt(null);
        cultivo.setDeletedAt(null);
        cultivo.setRestoredAt(null);
        return repository.save(cultivo);
    }

    @Override
    public Cultivo editar(Long id, Cultivo datos) { 
        return repository.findById(id).map(c -> {
            c.setParcela(datos.getParcela());
            c.setNombre(datos.getNombre());
            c.setTipoCultivo(datos.getTipoCultivo());
            c.setFrecuenciaRiegoDias(datos.getFrecuenciaRiegoDias());
            c.setTemperaturaIdeal(datos.getTemperaturaIdeal());
            c.setFechaSiembra(datos.getFechaSiembra());
            c.setRequiereSombra(datos.getRequiereSombra());
            c.setObservaciones(datos.getObservaciones());
            c.setUpdatedAt(LocalDateTime.now());
            return repository.save(c);
        }).orElseThrow(() -> new RuntimeException("Cultivo no encontrado"));
    }

    @Override
    @Transactional
    public Cultivo eliminar(Long id) { 
        return repository.findById(id).map(c -> {
            // Liberar parcela cuando se elimina cultivo
            Parcela parcela = c.getParcela();
            parcela.setEnUso(false);
            parcela.setCultivoActual(null);
            parcelaRepository.save(parcela);

            c.setEstado(false);
            c.setDeletedAt(LocalDateTime.now());
            return repository.save(c);
        }).orElse(null);
    }

    @Override
    public Cultivo restaurar(Long id) {
        return repository.findById(id).map(c -> {
            c.setEstado(true);
            c.setRestoredAt(LocalDateTime.now());
            return repository.save(c);
        }).orElse(null);
    }
}