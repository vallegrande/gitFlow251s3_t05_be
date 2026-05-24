package Agropacayales.edu.vallegrande.mybackend.repository;

import Agropacayales.valleGrande.model.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelaRepository extends JpaRepository<Parcela, Long> {
    List<Parcela> findByEstado(Boolean estado);
    boolean existsByNombre(String nombre);
}
