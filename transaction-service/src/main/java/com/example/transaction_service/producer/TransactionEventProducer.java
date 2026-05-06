package com.example.transaction_service.producer;

import com.example.transaction_service.event.TransactionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionEventProducer {

    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate; // here string is kafka message key and value is transactionevent

    @Value("${app.kafka.topic.transaction}")
    private String transactionTopic;

    public void publishTransactionEvent(TransactionEvent event) {

        kafkaTemplate.send(
                transactionTopic,
                event.getTransactionId().toString(),
                event
        );

        log.info("Kafka event published successfully: {}", event);
    }
}