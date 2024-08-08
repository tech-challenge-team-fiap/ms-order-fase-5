package br.com.fiap.techchallengeorder.application.usecases;

import br.com.fiap.techchallengeorder.adapter.gateways.OrderGatewayInterface;
import br.com.fiap.techchallengeorder.application.dto.order.OrderFormDto;
import br.com.fiap.techchallengeorder.application.dto.order.OrderListDto;
import br.com.fiap.techchallengeorder.application.dto.order.OrderResultFormDto;
import br.com.fiap.techchallengeorder.application.service.ClientService;
import br.com.fiap.techchallengeorder.application.service.impl.ProductServiceImpl;
import br.com.fiap.techchallengeorder.domain.enums.StatusOrder;
import br.com.fiap.techchallengeorder.domain.exception.InvalidProcessException;
import br.com.fiap.techchallengeorder.domain.exception.order.InvalidOrderProcessException;
import br.com.fiap.techchallengeorder.domain.exception.order.OrderNotFoundException;
import br.com.fiap.techchallengeorder.domain.interfaces.OrderUseCaseInterface;
import br.com.fiap.techchallengeorder.domain.interfaces.abstracts.AbstractOrderUseCase;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.ClientDB;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.NotificationDB;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.OrderDB;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.OrderQueueDB;
import br.com.fiap.techchallengeorder.external.infrastructure.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderUseCaseImpl extends AbstractOrderUseCase implements OrderUseCaseInterface {

    private static final Logger logger = LoggerFactory.getLogger(OrderUseCaseImpl.class);
    private final OrderGatewayInterface orderGatewayInterface;
    @Autowired
    ClientService iClientService;

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    public OrderUseCaseImpl(OrderRepository orderRepository,OrderGatewayInterface orderGatewayInterface) {
        super(orderRepository);

        this.orderGatewayInterface = orderGatewayInterface;
    }

    @Override
    public OrderResultFormDto register(OrderFormDto orderFormDto) throws InvalidProcessException {

        validateProduct(orderFormDto.getProducts());

        ClientDB client = null;
        if(orderFormDto.getClientId() != null){
            client = iClientService.clientFindById(orderFormDto.getClientId());
        }

        return orderGatewayInterface.register(orderFormDto, client);
    }

    @Override
    public OrderResultFormDto update(String numberOrder, String status) throws InvalidOrderProcessException {
        OrderDB order = findOrder(numberOrder);

        StatusOrder statusOrder = validateOrderStatus(status);

        order.markStatusAs(statusOrder);

        doUpdate(numberOrder, order, statusOrder);

        return orderGatewayInterface.update(order);
    }

    @Override
    public List<OrderListDto> findAll() {
        return orderGatewayInterface.findAll();
    }

    @Override
    public List<OrderDB> findAll(String status) {
        return orderGatewayInterface.findAll(status);
    }

    @Override
    public List<OrderListDto> getByStatusPayments(boolean isPayments) {
        return orderGatewayInterface.getByStatusPayments(isPayments);
    }

    @Override
    public OrderResultFormDto checkStatusPayments(String numberOrder) throws OrderNotFoundException {
        return orderGatewayInterface.checkStatusPayments(numberOrder);
    }

    private void doUpdate(String numberOrder, OrderDB order, StatusOrder statusOrder) {
        paymentReceived(numberOrder, statusOrder);

        clientOrderReady(numberOrder, order, statusOrder);

        clientOrderDelivered(numberOrder, order, statusOrder);
    }

    private void clientOrderDelivered(String numberOrder, OrderDB order, StatusOrder statusOrder) {
        if (StatusOrder.DELIVERED.equals(statusOrder)) {

            order.setDateLastUpdate(LocalDateTime.now());
            order.setDateDelivered(LocalDateTime.now());
            sendNotificationToClient(numberOrder, StatusOrder.DELIVERED);
        }
    }

    private void clientOrderReady(String numberOrder, OrderDB order, StatusOrder statusOrder) {
        if (StatusOrder.READY.equals(statusOrder)) {

            order.setDateLastUpdate(LocalDateTime.now());
            sendNotificationToClient(numberOrder, StatusOrder.READY);
        }
    }

    private void paymentReceived(String numberOrder, StatusOrder statusOrder) {
        if (StatusOrder.PAYMENTS_RECEIVED.equals(statusOrder)) {

            sendNotificationToClient(numberOrder, StatusOrder.RECEIVED);

            // Send order to queue IN_PREPARATION
            logger.info("[QUEUE] Sending the client order to preparation with order number: " + numberOrder);
            OrderQueueDB queue = OrderQueueDB.builder()
                    .numberOrder(numberOrder)
                    .statusOrder(StatusOrder.IN_PREPARATION)
                    .dateRegister(LocalDateTime.now())
                    .build();

            sendNotificationToClient(numberOrder, StatusOrder.IN_PREPARATION);

            orderGatewayInterface.saveOrder(queue);
        }
    }

    private void sendNotificationToClient(String numberOrder, StatusOrder status) {
        String msg = "[Notification] The client order with number: " + numberOrder + " - Status: " + status + ".";
        logger.info(msg);
        NotificationDB notification = NotificationDB.builder()
                .numberOrder(numberOrder)
                .message(msg)
                .statusOrder(status)
                .dateRegister(LocalDateTime.now())
                .build();
        orderGatewayInterface.sendNotification(notification);
    }
}