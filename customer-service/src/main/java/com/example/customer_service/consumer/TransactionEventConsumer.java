package com.example.customer_service.consumer;

import com.example.customer_service.event.TransactionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionEventConsumer {

    @KafkaListener(
            topics = "transaction-topic",
            groupId = "notification-group"
    )
    public void consumeTransactionEvent(TransactionEvent event) {

        try {
            log.info("Received transaction event: {} for amount {}",
                    event.getTransactionType(),
                    event.getAmount());

            log.info("Notification sent: {} for transactionId {}",
                    event.getMessage(),
                    event.getTransactionId());

        } catch (Exception e) {
            log.error("Error while processing transaction event: {}", event, e);
        }
    }
}