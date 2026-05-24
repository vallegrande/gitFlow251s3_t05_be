package Agropacayales.edu.vallegrande.mybackend.repository;

import Agropacayales.valleGrande.model.Cultivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CultivoRepository extends JpaRepository<Cultivo, Long> {
    List<Cultivo> findByEstado(Boolean estado);
    List<Cultivo> findByParcelaIdParcela(Long parcelaId);
}