package Agropacayales.valleGrande.service;

import Agropacayales.valleGrande.model.Parcela;

import java.util.List;
import java.util.Optional;

public interface ParcelaService {
    List<Parcela> listarTodos();
    Optional<Parcela> listarPorId(Long id);
    List<Parcela> listarPorEstado(Boolean estado);
    Parcela crear(Parcela parcela);
    Parcela editar(Long id, Parcela parcela);
    Parcela eliminar(Long id);
    Parcela restaurar(Long id);
}
