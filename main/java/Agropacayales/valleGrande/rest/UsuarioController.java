package Agropacayales.valleGrande.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Agropacayales.valleGrande.model.Usuario;
import Agropacayales.valleGrande.service.IUsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuario-Controller", description = "Operaciones de gestión de usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService service;

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Obtiene la lista de todos los usuarios registrados")
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Obtiene un usuario específico por su ID")
    public ResponseEntity<Usuario> buscar(@PathVariable Integer id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET - Listar usuarios por estado
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar por estado", description = "Filtra usuarios activos o inactivos")
    public ResponseEntity<List<Usuario>> listarPorEstado(@PathVariable Boolean estado) {
        return ResponseEntity.ok(service.listarPorEstado(estado));
    }

    @PostMapping
    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario en el sistema")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        Usuario nuevo = service.guardar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Modifica los datos de un usuario existente")
    public ResponseEntity<Usuario> editar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(service.actualizar(id, usuario));
    }

    // Patrón de URL consistente: /{id}/eliminar (igual que Producto y Parcela)
    @PatchMapping("/{id}/eliminar")
    @Operation(summary = "Eliminar (Lógico)", description = "Desactiva al usuario cambiando su estado a false")
    public ResponseEntity<Usuario> eliminar(@PathVariable Integer id) {
        Usuario eliminado = service.eliminarLogico(id);
        if (eliminado != null) {
            return ResponseEntity.ok(eliminado);
        }
        return ResponseEntity.notFound().build();
    }

    // Patrón de URL consistente: /{id}/restaurar (igual que Producto y Parcela)
    @PatchMapping("/{id}/restaurar")
    @Operation(summary = "Restaurar", description = "Activa nuevamente al usuario cambiando su estado a true")
    public ResponseEntity<Usuario> restaurar(@PathVariable Integer id) {
        Usuario restaurado = service.restaurarLogico(id);
        if (restaurado != null) {
            return ResponseEntity.ok(restaurado);
        }
        return ResponseEntity.notFound().build();
    }
}