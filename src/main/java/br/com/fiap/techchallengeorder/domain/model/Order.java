package br.com.fiap.techchallengeorder.domain.model;

import br.com.fiap.techchallengeorder.domain.enums.PaymentsType;
import br.com.fiap.techchallengeorder.domain.enums.StatusOrder;
import br.com.fiap.techchallengeorder.domain.enums.TypeProduct;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.ClientDB;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.OrderDB;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.ProductDB;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Order {

    private ClientDB client;

    private String numberOrder;

    private Date date;

    private StatusOrder statusOrder;

    private BigDecimal total;

    private PaymentsType paymentsType;

    private TypeProduct typeProduct;

    private Integer paymentId;

    private LocalDateTime dateDelivered;

    private LocalDateTime dateLastUpdate;

    private List<ProductDB> products;

    public Order(ClientDB client, String numberOrder, Date date, StatusOrder statusOrder, BigDecimal total, PaymentsType paymentsType, Integer paymentId, LocalDateTime dateDelivered, LocalDateTime dateLastUpdate, List<ProductDB> products) {
        this.client = client;
        this.numberOrder = numberOrder;
        this.date = date;
        this.statusOrder = statusOrder;
        this.total = total;
        this.paymentsType = paymentsType;
        this.paymentId = paymentId;
        this.dateDelivered = dateDelivered;
        this.dateLastUpdate = dateLastUpdate;
        this.products = products;
    }

    public OrderDB build() {
        return OrderDB.builder()
                                .client(getClient())
                                .numberOrder(getNumberOrder())
                                .date(new Date())
                                .statusOrder(getStatusOrder())
                                .total(getTotal())
                                .paymentsType(getPaymentsType())
                                .dateDelivered(null)
                                .dateLastUpdate(LocalDateTime.now())
                                .products(getProducts())
                                .build();
    }
}