package br.com.fiap.techchallengeorder.external.infrastructure.gateway;

import br.com.fiap.techchallengeorder.adapter.gateways.OrderGatewayInterface;
import br.com.fiap.techchallengeorder.application.service.ProductService;
import br.com.fiap.techchallengeorder.domain.exception.InvalidProcessException;
import br.com.fiap.techchallengeorder.domain.model.Order;
import br.com.fiap.techchallengeorder.application.dto.order.OrderFormDto;
import br.com.fiap.techchallengeorder.application.dto.order.OrderListDto;
import br.com.fiap.techchallengeorder.application.dto.order.OrderResultFormDto;
import br.com.fiap.techchallengeorder.application.dto.product.ProductOrderFormDto;
import br.com.fiap.techchallengeorder.domain.enums.PaymentsType;
import br.com.fiap.techchallengeorder.domain.enums.StatusOrder;
import br.com.fiap.techchallengeorder.domain.exception.BaseException;
import br.com.fiap.techchallengeorder.domain.exception.order.InvalidOrderProcessException;
import br.com.fiap.techchallengeorder.domain.exception.order.InvalidProductStorageException;
import br.com.fiap.techchallengeorder.domain.exception.order.OrderNotFoundException;
import br.com.fiap.techchallengeorder.domain.utils.NumberOrderGenerator;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.*;
import br.com.fiap.techchallengeorder.external.infrastructure.repositories.NotificationRepository;
import br.com.fiap.techchallengeorder.external.infrastructure.repositories.OrderQueueRepository;
import br.com.fiap.techchallengeorder.external.infrastructure.repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.*;

import br.com.fiap.techchallengeorder.external.infrastructure.webhook.Payments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class OrderGatewayImpl implements OrderGatewayInterface {

    private static final Logger logger = LoggerFactory.getLogger(OrderGatewayImpl.class);

    private final OrderRepository orderRepository;

    @Autowired
    ProductService iProductService;

    private final OrderQueueRepository orderQueueRepository;

    private final NotificationRepository notificationRepository;

    private final Payments payments;

    @Autowired
    public OrderGatewayImpl(OrderRepository orderRepository, OrderQueueRepository orderQueueRepository, NotificationRepository notificationRepository, Payments payments){
        this.orderRepository = orderRepository;
        this.orderQueueRepository = orderQueueRepository;
        this.notificationRepository = notificationRepository;
        this.payments = payments;
    }

    @Override
    public OrderResultFormDto register(OrderFormDto orderFormDto, ClientDB client) throws InvalidProcessException {
        List<UUID> productsIds = orderFormDto.getProducts().stream().map(ProductOrderFormDto::getId).collect(Collectors.toList());

        AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);
        List<ProductDB> products = (List<ProductDB>) iProductService.productFindAllById(productsIds);

        // Calculated value total of list products
        for (ProductDB prod : products) {
            total.updateAndGet(v -> v.add(prod.getPrice()));

            if (prod.hasStorage()) {
                prod.mergeQuantity(1);
                iProductService.edit(prod);
            } else {
                throw new InvalidProductStorageException(prod.getId());
            }
        }

        if (total.get().intValue() == 0) {
            new BaseException("Error calculating total price orders");
        }

        //Generated number order
        String numberOrder = NumberOrderGenerator.generateNumberOrder();

        Order productOrder = new Order(client, numberOrder, new Date(), StatusOrder.IN_PREPARATION, total.get(), PaymentsType.QR_CODE, null, null, LocalDateTime.now(), products);

        return doRegister(productOrder.build());
    }

    public OrderResultFormDto doRegister(OrderDB orderDB) {
        OrderDB orderSaveDB = orderRepository.save(orderDB);
        return new OrderResultFormDto(orderSaveDB);
    }

    @Override
    public OrderResultFormDto update(OrderDB order) throws InvalidOrderProcessException {
        OrderDB orderSaveDB = orderRepository.save(order);
        return new OrderResultFormDto(orderSaveDB);
    }

    public List<OrderDB> findAll(String status) {
        StatusOrder statusOrder = StatusOrder.valueOf(status.toUpperCase());
        List<OrderDB> itens = orderRepository
                .findAllByStatusOrder(Sort.by(Sort.Direction.ASC, "date"), statusOrder )
                .stream()
                .toList();

        return itens;
    }

    public List<OrderListDto> findAll() {
        List<OrderListDto> allOrders = new ArrayList<>();

        //READY
        List<OrderQueueDB> orderWithStatusRead = orderQueueRepository
                .findAllByStatusOrder(Sort.by(Sort.Direction.DESC, "dateRegister"), StatusOrder.READY);

        orderWithStatusRead.forEach(order -> allOrders.add(new OrderListDto(order)));

        //IN_PREPARATION
        List<OrderQueueDB> orderWithStatusPreparation = orderQueueRepository
                .findAllByStatusOrder(Sort.by(Sort.Direction.DESC, "dateRegister"), StatusOrder.IN_PREPARATION);

        orderWithStatusPreparation.forEach(order -> allOrders.add(new OrderListDto(order)));

        //RECEIVED
        List<OrderDB> orderWithStatusReceived = orderRepository
                .findAllByStatusOrder(Sort.by(Sort.Direction.DESC, "date"), StatusOrder.WAITING_PAYMENTS);

        orderWithStatusReceived.forEach(order -> allOrders.add(new OrderListDto(order)));

        return allOrders;
    }


    @Override
    public OrderResultFormDto checkStatusPayments(String numberOrder) throws OrderNotFoundException {
        Optional<OrderDB> order = orderRepository.findByNumberOrder(numberOrder);
        if(order.isPresent()) {
            boolean isPay = order.get().getStatusOrder().equals(StatusOrder.PAYMENTS_RECEIVED);

            if(isPay){
                return new OrderResultFormDto(order.get());
            }

            return new OrderResultFormDto(StatusOrder.WAITING_PAYMENTS);
        } else {
            throw new OrderNotFoundException(numberOrder);
        }
    }
    public List<OrderListDto> getByStatusPayments(boolean isPayments) {
        List<OrderListDto> allOrders = new ArrayList<>();
        StatusOrder status = isPayments ? StatusOrder.PAYMENTS_RECEIVED : StatusOrder.WAITING_PAYMENTS;

        List<OrderDB> orders = orderRepository
                .findAllByStatusOrder(Sort.by(Sort.Direction.DESC, "date"), status );

        orders.forEach(order -> allOrders.add(new OrderListDto(order)));

        return allOrders;
    }

    @Override
    public void saveOrder(OrderQueueDB queue) {
        logger.info("[NOTIFICATION] Save order with successfully");
        orderQueueRepository.save(queue);
    }

    @Override
    public void sendNotification(NotificationDB notification) {
        logger.info("[NOTIFICATION] Send notification successfully");
        notificationRepository.save(notification);
    }
}