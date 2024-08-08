package br.com.fiap.techchallengeorder.external.infrastructure.entities;

import br.com.fiap.techchallengeorder.application.dto.product.ProductEditFormDto;
import br.com.fiap.techchallengeorder.domain.enums.TypeProduct;
import br.com.fiap.techchallengeorder.domain.enums.TypeStatus;
import br.com.fiap.techchallengeorder.domain.type.StringRepresentationUUIDType;
import com.github.f4b6a3.ulid.UlidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "PRODUCT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class ProductDB {

    @Id
    @Builder.Default
    @Type(StringRepresentationUUIDType.class)
    @Column(name = "ID")
    @NotNull
    @EqualsAndHashCode.Include
    private UUID id = nextId();

    @Column(name = "NAME")
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "DESCRIPTION")
    @EqualsAndHashCode.Exclude
    private String description;

    @Column(name = "QUANTITY")
    @EqualsAndHashCode.Exclude
    private Integer quantity;

    @Column(name = "TYPE_PRODUCT")
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Exclude
    private TypeProduct typeProduct;

    @Column(name = "PRICE")
    @EqualsAndHashCode.Exclude
    private BigDecimal price;

    @Column(name = "TYPE_STATUS")

    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Exclude
    private TypeStatus typeStatus;

    @Column(name = "DATE_REGISTER")
    @EqualsAndHashCode.Exclude
    private LocalDateTime dateRegister;

    @NotNull
    private static UUID nextId() {
        return UlidCreator.getMonotonicUlid().toUuid();
    }

    public void mergeQuantity(Integer remove) {
        this.quantity = this.quantity - remove;
    }

    public boolean hasStorage() {
        return this.quantity > 0;
    }

    public void updateFrom(ProductEditFormDto productFormEditDto) {
        this.name = productFormEditDto.getName();
        this.description = productFormEditDto.getDescription();
        this.quantity = productFormEditDto.getQuantity();
        this.typeProduct = productFormEditDto.getTypeProduct();
        this.price = productFormEditDto.getPrice();
        this.typeStatus = productFormEditDto.getTypeStatus();
    }
}