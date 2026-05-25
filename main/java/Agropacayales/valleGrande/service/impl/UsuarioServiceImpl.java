package Agropacayales.valleGrande.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Agropacayales.valleGrande.model.Usuario;
import Agropacayales.valleGrande.repository.UsuarioRepository;
import Agropacayales.valleGrande.service.IUsuarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Usuario> listarPorEstado(Boolean estado) {
        return repository.findByEstado(estado);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setEstado(true);
        return repository.save(usuario);
    }

    @Override
    public Usuario actualizar(Integer id, Usuario datos) {
        return repository.findById(id).map(u -> {
            u.setNombre(datos.getNombre());
            u.setApellido(datos.getApellido());
            u.setCorreo(datos.getCorreo());
            u.setPassword(datos.getPassword());
            u.setRol(datos.getRol());
            u.setFechaContratacion(datos.getFechaContratacion());
            u.setUpdatedAt(LocalDateTime.now());
            return repository.save(u);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public Usuario eliminarLogico(Integer id) {
        return repository.findById(id).map(u -> {
            u.setEstado(false);
            u.setDeletedAt(LocalDateTime.now());
            return repository.save(u);
        }).orElse(null);
    }

    @Override
    public Usuario restaurarLogico(Integer id) {
        return repository.findById(id).map(u -> {
            u.setEstado(true);
            u.setRestoredAt(LocalDateTime.now());
            return repository.save(u);
        }).orElse(null);
    }
}