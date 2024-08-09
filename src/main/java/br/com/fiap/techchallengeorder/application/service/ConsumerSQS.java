package br.com.fiap.techchallengeorder.application.service;

import br.com.fiap.techchallengeorder.domain.model.ProductMessage;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

@Service
@Log4j2
public class ConsumerSQS {

    private final AmazonSQS amazonSQSClient;
    private final ObjectMapper objectMapper;

    public ConsumerSQS(AmazonSQS amazonSQSClient, ObjectMapper objectMapper) {
        this.amazonSQSClient = amazonSQSClient;
        this.objectMapper = objectMapper;
    }

    private void sendUpdateToProductMicroservice(ProductMessage productMessage) {
        try {
            String queueUrl = amazonSQSClient.getQueueUrl("product-queue").getQueueUrl();
            String messageBody = objectMapper.writeValueAsString(productMessage);
            amazonSQSClient.sendMessage(queueUrl, messageBody);
            log.info("Sent update to product microservice: {}", messageBody);
        } catch (Exception e) {
            log.error("Failed to send update to product microservice: {}", e.getMessage());
        }
    }

    @Scheduled(cron = "0 */1 * ? * *") // It runs every 5 seconds.
    public void consumeMessages() {
        try {
            String queueUrl = amazonSQSClient.getQueueUrl("payments-error").getQueueUrl();

            ReceiveMessageResult receiveMessageResult = amazonSQSClient.receiveMessage(queueUrl);

            if (!receiveMessageResult.getMessages().isEmpty()) {
                com.amazonaws.services.sqs.model.Message message = receiveMessageResult.getMessages().get(0);
                log.info("Read Message from queue: {}", message.getBody());

                ProductMessage productMessage = objectMapper.readValue(message.getBody(), ProductMessage.class);

                log.info("Updating product with ID: {} to new quantity: {}", productMessage.getId(), productMessage.getQuantity());

                sendUpdateToProductMicroservice(productMessage);

                amazonSQSClient.deleteMessage(queueUrl, message.getReceiptHandle());
            }

        } catch (Exception e) {
            log.error("Queue Exception Message: {}", e.getMessage());
        }
    }
}