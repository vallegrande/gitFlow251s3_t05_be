package Agropacayales.valleGrande.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * DTO para crear transacción de cultivo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CultivoRequestDTO {

    private Long parcelaId;
    private String nombre;
    private String tipoCultivo;
    private BigDecimal areaSembrada;
    private String responsable;
    private Integer frecuenciaRiegoDias;
    private Double temperaturaIdeal;
    private Boolean requiereSombra;
    private String observaciones;
}