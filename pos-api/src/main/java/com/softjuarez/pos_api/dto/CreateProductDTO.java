package com.softjuarez.pos_api.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a cero")
    private Double price;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
}