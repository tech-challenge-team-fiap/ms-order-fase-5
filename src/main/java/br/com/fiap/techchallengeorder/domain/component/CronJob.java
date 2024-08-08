package br.com.fiap.techchallengeorder.domain.component;

import br.com.fiap.techchallengeorder.domain.enums.StatusOrder;
import br.com.fiap.techchallengeorder.external.infrastructure.repositories.NotificationRepository;
import br.com.fiap.techchallengeorder.external.infrastructure.repositories.OrderQueueRepository;
import br.com.fiap.techchallengeorder.external.infrastructure.repositories.OrderRepository;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.NotificationDB;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.OrderQueueDB;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.OrderDB;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class CronJob {

    private static final Logger logger = LoggerFactory.getLogger(CronJob.class);
    private OrderQueueRepository orderQueueRepository;
    private NotificationRepository notificationRepository;
    private OrderRepository orderRepository;

    @Autowired
    public CronJob(OrderQueueRepository orderQueueRepository, NotificationRepository notificationRepository, OrderRepository orderRepository){
        this.orderQueueRepository = orderQueueRepository;
        this.notificationRepository = notificationRepository;
        this.orderRepository = orderRepository;
    }

    @Scheduled(cron = "0 */1 * ? * *")
    public void finishedOrder() {
        System.out.println("=========== [COOK-ROOM] FINISHING CLIENT ORDER ========== ");

        OrderQueueDB item = clientOrderPreparation();

        if (item != null) {
            logger.info("[QUEUE] Client order Id:" + item.getId() + "/ Number order: " + item.getNumberOrder() + "/ Phase:" + item.getStatusOrder());
            item.setStatusOrder(StatusOrder.READY);

            orderQueueRepository.save(item);

            NotificationDB notification = NotificationDB.builder()
                .numberOrder(item.getNumberOrder())
                .message("Finishing client order: " + item.getNumberOrder())
                .statusOrder(StatusOrder.READY)
                .dateRegister(LocalDateTime.now())
                .build();

            logger.info("[Notification] Notifying was sent to client that client order is READY");
            notificationRepository.save(notification);

            doFinishedOrder(item);
        } else {
            logger.info("[QUEUE] Does not have any client order to process");
        }
    }

    private void doFinishedOrder(OrderQueueDB item) {
        OrderDB order = orderRepository.findByNumberOrder(item.getNumberOrder()).get();
        order.setStatusOrder(StatusOrder.READY);
        order.setDateLastUpdate(LocalDateTime.now());

        logger.info("[QUEUE] Client order Id:" + order.getId() + "/ Number order: " + order.getNumberOrder() + "/ Phase:" + order.getStatusOrder());
        orderRepository.save(order);
    }

    private OrderQueueDB clientOrderPreparation() {
        return orderQueueRepository
            .findAllByStatusOrder(Sort.by(Sort.Direction.ASC, "dateRegister"), StatusOrder.IN_PREPARATION)
            .stream()
            .findFirst()
            .orElse(null);
    }
}