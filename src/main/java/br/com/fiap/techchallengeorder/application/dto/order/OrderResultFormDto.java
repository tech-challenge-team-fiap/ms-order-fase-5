package br.com.fiap.techchallengeorder.application.dto.order;

import br.com.fiap.techchallengeorder.domain.enums.StatusOrder;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.OrderDB;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResultFormDto {

    private String numberOrder;
    private Date date;
    private String statusOrder;
    private BigDecimal total;
    private String paymentsType;

    public OrderResultFormDto(OrderDB order) {
        this.numberOrder = order.getNumberOrder();
        this.date = order.getDate();
        this.statusOrder = order.getStatusOrder().name();
        this.total = order.getTotal();
        this.paymentsType = order.getPaymentsType().name();
    }

    public OrderResultFormDto(StatusOrder status) {
        this.statusOrder = status.name();
    }
}