package br.com.fiap.techchallengeorder.domain.interfaces;

import br.com.fiap.techchallengeorder.application.dto.order.OrderFormDto;
import br.com.fiap.techchallengeorder.application.dto.order.OrderListDto;
import br.com.fiap.techchallengeorder.application.dto.order.OrderResultFormDto;
import br.com.fiap.techchallengeorder.domain.exception.InvalidProcessException;
import br.com.fiap.techchallengeorder.domain.exception.order.InvalidOrderProcessException;
import br.com.fiap.techchallengeorder.domain.exception.order.OrderNotFoundException;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.OrderDB;

import java.util.List;

public interface OrderUseCaseInterface {

    OrderResultFormDto register(final OrderFormDto orderFormDto) throws InvalidProcessException;

    OrderResultFormDto update(String numberOrder, String status) throws InvalidOrderProcessException;

    List<OrderListDto> findAll();

    List<OrderDB> findAll(String status);

    List<OrderListDto> getByStatusPayments(boolean isPayments);

    OrderResultFormDto checkStatusPayments(String numberOrder) throws OrderNotFoundException;
}
