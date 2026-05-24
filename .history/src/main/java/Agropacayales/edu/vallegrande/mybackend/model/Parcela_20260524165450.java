package Agropacayales.edu.vallegrande.mybackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad Parcela - Representa los terrenos de cultivo de la empresa.
 * Corresponde a US1: Registro de terrenos de cultivo.
 */
@Entity
@Table(name = "parcelas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parcela")
    private Long idParcela;

    @Column(name = "nombre", nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(name = "ubicacion", length = 200)
    private String ubicacion;

    @Column(name = "area_hectareas", precision = 10, scale = 2)
    private BigDecimal areaHectareas;

    @Column(name = "tipo_suelo", length = 80)
    private String tipoSuelo;

    @Column(name = "responsable", length = 100)
    private String responsable;

    @Column(name = "estado_riego", length = 50)
    private String estadoRiego;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_ultima_siembra")
    private LocalDate fechaUltimaSiembra;

    @Column(name = "produccion_estimada", length = 100)
    private String produccionEstimada;

    @Column(name = "cultivo_actual", length = 100)
    private String cultivoActual;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "en_uso")
    private Boolean enUso = false;

    @Column(name = "estado")
    private Boolean estado = true;

    // Campos de auditoría
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Lima")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Lima")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Lima")
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Lima")
    @Column(name = "restored_at")
    private LocalDateTime restoredAt;
}
