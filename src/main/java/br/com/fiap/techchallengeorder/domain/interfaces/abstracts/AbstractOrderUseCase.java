package br.com.fiap.techchallengeorder.domain.interfaces.abstracts;

import br.com.fiap.techchallengeorder.application.dto.product.ProductOrderFormDto;
import br.com.fiap.techchallengeorder.application.service.ProductService;
import br.com.fiap.techchallengeorder.domain.enums.StatusOrder;
import br.com.fiap.techchallengeorder.domain.exception.InvalidProcessException;
import br.com.fiap.techchallengeorder.domain.exception.order.InvalidOrderProcessException;
import br.com.fiap.techchallengeorder.domain.exception.order.InvalidOrderProductException;
import br.com.fiap.techchallengeorder.domain.exception.order.InvalidOrderStatusException;
import br.com.fiap.techchallengeorder.domain.exception.order.OrderNotFoundException;
import br.com.fiap.techchallengeorder.domain.exception.products.ProductNotFoundException;
import br.com.fiap.techchallengeorder.external.infrastructure.repositories.OrderRepository;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.OrderDB;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public abstract class AbstractOrderUseCase {

    private final OrderRepository orderRepository;

    @Autowired
    ProductService iproductService;

    protected void validateProduct(List<ProductOrderFormDto> products) throws InvalidProcessException {
        if (products.isEmpty()) {
            throw new InvalidOrderProductException();
        } else {
            doValidateProduct(products);
        }
    }

    private void doValidateProduct(List<ProductOrderFormDto> products) throws ProductNotFoundException {
        for (ProductOrderFormDto product : products) {
            if (!iproductService.productFindById(product.getId())) {
                throw new ProductNotFoundException(product.getId());
            }
        }
    }

    public OrderDB findOrder(String numberOrder) throws InvalidOrderProcessException {
        Optional<OrderDB> order = orderRepository.findByNumberOrder(numberOrder);
        if(order.isPresent()) {
            return order.get();
        } else {
            throw new OrderNotFoundException(numberOrder);
        }
    }

    public StatusOrder validateOrderStatus(String status) throws InvalidOrderProcessException {
        try {
            return StatusOrder.valueOf(status);
        } catch (Exception ex) {
            throw new InvalidOrderStatusException(status);
        }

    }
}
