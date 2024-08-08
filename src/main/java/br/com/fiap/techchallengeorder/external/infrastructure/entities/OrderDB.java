package br.com.fiap.techchallengeorder.external.infrastructure.entities;

import br.com.fiap.techchallengeorder.domain.enums.PaymentsType;
import br.com.fiap.techchallengeorder.domain.enums.StatusOrder;
import br.com.fiap.techchallengeorder.domain.type.StringRepresentationUUIDType;
import com.github.f4b6a3.ulid.UlidCreator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "CLIENT_ORDER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class OrderDB {

    @Id
    @Builder.Default
    @Type(StringRepresentationUUIDType.class)
    @Column(name = "ID")
    @NotNull
    @EqualsAndHashCode.Include
    private UUID id = nextId();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = ClientDB.class)
    @JoinColumn(name = "CLIENT_ID")
    private ClientDB client;

    @Column(name = "ORDER_NUMBER")
    private String numberOrder;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "ORDER_STATUS")
    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;

    @Column(name = "TOTAL")
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAYMENT_TYPE")
    private PaymentsType paymentsType;

    @Column(name = "PAYMENT_ID")
    @Type(StringRepresentationUUIDType.class)
    @Builder.Default
    private UUID paymentId = nextId();

    @Column(name = "DATE_DELIVERED")
    private LocalDateTime dateDelivered;

    @CreatedDate
    @Column(name = "UPDATED_AT")
    private LocalDateTime dateLastUpdate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ORDER_PRODUCT",
            joinColumns = @JoinColumn(name = "ORDER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")
    )
    private List<ProductDB> products;

    @NotNull
    private static UUID nextId() {
        return UlidCreator.getMonotonicUlid().toUuid();
    }

    public OrderDB(ClientDB client, String numberOrder, Date date, StatusOrder statusOrder, BigDecimal total, PaymentsType paymentsType, LocalDateTime dateDelivered, LocalDateTime dateLastUpdate, List<ProductDB> products) {

        this.client = client;
        this.numberOrder = numberOrder;
        this.date = date;
        this.statusOrder = statusOrder;
        this.total = total;
        this.paymentsType = paymentsType;
        this.dateDelivered = dateDelivered;
        this.dateLastUpdate = dateLastUpdate;
        this.products = products;
    }

    public void markStatusAs(StatusOrder status) {
        this.statusOrder = status;
    }
}