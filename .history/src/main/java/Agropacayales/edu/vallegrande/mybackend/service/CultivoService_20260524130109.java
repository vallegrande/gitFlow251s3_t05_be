package Agropacayales.valleGrande.service;

import Agropacayales.valleGrande.dto.CultivoRequestDTO;
import Agropacayales.valleGrande.model.Cultivo;
import java.util.List;
import java.util.Optional;

public interface CultivoService {
    List<Cultivo> listarTodos();
    Optional<Cultivo> listarPorId(Long id);
    List<Cultivo> listarPorEstado(Boolean estado);
    List<Cultivo> listarPorParcela(Long parcelaId);
    
    // TRANSACCIÓN FUNCIONAL
    Cultivo crearSiembra(CultivoRequestDTO request);
    
    Cultivo crear(Cultivo cultivo);
    Cultivo editar(Long id, Cultivo cultivo);
    Cultivo eliminar(Long id);
    Cultivo restaurar(Long id);
}