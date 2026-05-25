package Agropacayales.valleGrande.service;

import Agropacayales.valleGrande.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> listarTodos();
    Optional<Usuario> buscarPorId(Integer id);
    List<Usuario> listarPorEstado(Boolean estado);
    Usuario guardar(Usuario usuario);
    Usuario actualizar(Integer id, Usuario usuario);
    Usuario eliminarLogico(Integer id);
    Usuario restaurarLogico(Integer id);
}