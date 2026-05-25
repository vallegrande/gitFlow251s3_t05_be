package Agropacayales.valleGrande.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Agropacayales.valleGrande.model.Usuario;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> { 
    List<Usuario> findByEstado(Boolean estado);
}