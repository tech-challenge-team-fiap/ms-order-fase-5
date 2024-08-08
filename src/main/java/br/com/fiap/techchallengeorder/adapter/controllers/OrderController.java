package br.com.fiap.techchallengeorder.adapter.controllers;

import static br.com.fiap.techchallengeorder.domain.utils.ProblemAware.problemOf;

import br.com.fiap.techchallengeorder.application.dto.order.OrderFormDto;
import br.com.fiap.techchallengeorder.domain.exception.order.OrderNotFoundException;
import br.com.fiap.techchallengeorder.domain.interfaces.OrderUseCaseInterface;
import br.com.fiap.techchallengeorder.domain.exception.InvalidProcessException;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderController {

    private final OrderUseCaseInterface orderUseCase;

    @Autowired
    public OrderController(OrderUseCaseInterface orderUseCaseInterface){
        this.orderUseCase = orderUseCaseInterface;
    }
    public ResponseEntity<?> register(@RequestBody OrderFormDto orderFormDto) {
        try {
            return ResponseEntity.ok(orderUseCase.register(orderFormDto));
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    public ResponseEntity<?> update(@PathParam("numberOrder") String numberOrder, @PathParam("status") String status) {
        try {
            return ResponseEntity.ok(orderUseCase.update(numberOrder, status));
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    public ResponseEntity<?> findAll(@PathVariable("status") String status) {
        return ResponseEntity.ok(orderUseCase.findAll(status));
    }

    public ResponseEntity<?> checkStatusPayments(@PathVariable("numberOrder") String numberOrder) throws OrderNotFoundException {
        return ResponseEntity.ok(orderUseCase.checkStatusPayments(numberOrder));
    }

    public ResponseEntity<?> getByStatusPayments(@PathVariable("payment") boolean isPayments){
        return ResponseEntity.ok(orderUseCase.getByStatusPayments(isPayments));
    }

    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(orderUseCase.findAll());
    }

}
