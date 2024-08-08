package br.com.fiap.techchallengeorder.application.dto.order;

import br.com.fiap.techchallengeorder.domain.enums.PaymentsType;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.OrderQueueDB;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.OrderDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderListDto {

    private String numberOrder;

    private String date;

    private String statusOrder;

    private String paymentsType;


    public OrderListDto (OrderQueueDB orderQueue) {
        this.setNumberOrder(orderQueue.getNumberOrder());
        this.setDate(String.valueOf(orderQueue.getDateRegister()));
        this.setStatusOrder(orderQueue.getStatusOrder().name());
        this.setPaymentsType(PaymentsType.QR_CODE.name());
    }

    public OrderListDto(OrderDB order) {
        this.setNumberOrder(order.getNumberOrder());
        this.setDate(String.valueOf(order.getDate().getTime()));
        this.setStatusOrder(order.getStatusOrder().name());
        this.setPaymentsType(PaymentsType.QR_CODE.name());
    }
}