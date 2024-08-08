package br.com.fiap.techchallengeorder.application.dto.product;

import br.com.fiap.techchallengeorder.domain.enums.TypeProduct;
import br.com.fiap.techchallengeorder.domain.enums.TypeStatus;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.ProductDB;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

    private String id;
    private String name;
    private String description;
    private Integer quantity;
    private TypeProduct typeProduct;
    private BigDecimal price;
    private TypeStatus typeStatus;
    private LocalDateTime dateRegister;

    public ProductDto(String name, String description, Integer quantity, TypeProduct typeProduct, BigDecimal price, TypeStatus typeStatus, LocalDateTime dateRegister) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.typeProduct = typeProduct;
        this.price = price;
        this.typeStatus = typeStatus;
        this.dateRegister = dateRegister;
    }

    public ProductDto(UUID id){
        this.id = id.toString();
    }

    public static ProductDto toDto(ProductDB product) {
        return new ProductDto(product.getName(),product.getDescription(),product.getQuantity(),product.getTypeProduct(),product.getPrice(),product.getTypeStatus(),product.getDateRegister());
    }
}
