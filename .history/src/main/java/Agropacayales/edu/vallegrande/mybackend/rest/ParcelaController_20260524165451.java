package Agropacayales.edu.vallegrande.mybackend.rest;

import Agropacayales.valleGrande.model.Parcela;
import Agropacayales.valleGrande.service.ParcelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parcelas")
@Tag(name = "Parcela-Controller", description = "Gestión de parcelas / terrenos de cultivo")
public class ParcelaController {

    @Autowired
    private ParcelaService parcelaService;

    // GET - Listar todas las parcelas
    @GetMapping
    @Operation(summary = "Listar parcelas", description = "Obtiene la lista de todas las parcelas")
    public ResponseEntity<List<Parcela>> listarTodos() {
        return ResponseEntity.ok(parcelaService.listarTodos());
    }

    // GET - Listar parcela por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Obtiene una parcela por su ID")
    public ResponseEntity<Parcela> listarPorId(@PathVariable Long id) {
        Optional<Parcela> parcela = parcelaService.listarPorId(id);
        return parcela.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET - Listar parcelas por estado
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar por estado", description = "Filtra parcelas activas o inactivas")
    public ResponseEntity<List<Parcela>> listarPorEstado(@PathVariable Boolean estado) {
        return ResponseEntity.ok(parcelaService.listarPorEstado(estado));
    }

    // POST - Crear nueva parcela
    @PostMapping
    @Operation(summary = "Crear parcela", description = "Registra una nueva parcela")
    public ResponseEntity<Parcela> crear(@RequestBody Parcela parcela) {
        Parcela nueva = parcelaService.crear(parcela);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    // PUT - Editar parcela existente
    @PutMapping("/{id}")
    @Operation(summary = "Editar parcela", description = "Modifica los datos de una parcela")
    public ResponseEntity<Parcela> editar(@PathVariable Long id, @RequestBody Parcela parcela) {
        Parcela editada = parcelaService.editar(id, parcela);
        if (editada != null) {
            return ResponseEntity.ok(editada);
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH - Eliminar lógico
    @PatchMapping("/{id}/eliminar")
    @Operation(summary = "Eliminar (lógico)", description = "Desactiva la parcela")
    public ResponseEntity<Parcela> eliminar(@PathVariable Long id) {
        Parcela eliminada = parcelaService.eliminar(id);
        if (eliminada != null) {
            return ResponseEntity.ok(eliminada);
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH - Restaurar lógico
    @PatchMapping("/{id}/restaurar")
    @Operation(summary = "Restaurar", description = "Reactiva la parcela")
    public ResponseEntity<Parcela> restaurar(@PathVariable Long id) {
        Parcela restaurada = parcelaService.restaurar(id);
        if (restaurada != null) {
            return ResponseEntity.ok(restaurada);
        }
        return ResponseEntity.notFound().build();
    }
}
