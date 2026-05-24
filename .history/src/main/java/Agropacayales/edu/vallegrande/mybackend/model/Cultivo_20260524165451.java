package Agropacayales.edu.vallegrande.mybackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cultivos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cultivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cultivo")
    private Long idCultivo;

    @ManyToOne
    @JoinColumn(name = "id_parcela", nullable = false)
    private Parcela parcela;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "tipo_cultivo", nullable = false, length = 80)
    private String tipoCultivo;

    // CAMPOS TRANSACCIONALES CALCULABLES
    @Column(name = "area_sembrada", precision = 10, scale = 2)
    private BigDecimal areaSembrada;

    @Column(name = "costo_siembra", precision = 10, scale = 2)
    private BigDecimal costoSiembra;

    @Column(name = "produccion_estimada_kg", precision = 10, scale = 2)
    private BigDecimal produccionEstimadaKg;

    @Column(name = "responsable", length = 100)
    private String responsable;

    // CAMPOS TÉCNICOS
    @Column(name = "frecuencia_riego_dias", nullable = false)
    private Integer frecuenciaRiegoDias;

    @Column(name = "temperatura_ideal", nullable = false)
    private Double temperaturaIdeal;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_siembra")
    private LocalDate fechaSiembra;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_cosecha_estimada")
    private LocalDate fechaCosechaEstimada;

    @Column(name = "requiere_sombra")
    private Boolean requiereSombra = false;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "estado")
    private Boolean estado = true;

    // CAMPOS DE AUDITORÍA
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