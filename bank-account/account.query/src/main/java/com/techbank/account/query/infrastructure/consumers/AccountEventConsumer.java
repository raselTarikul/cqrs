package com.techbank.account.query.infrastructure.consumers;

import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundDepositedEvent;
import com.techbank.account.common.events.FundWithDrownEvent;
import com.techbank.account.query.infrastructure.handlers.EventHandlers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class AccountEventConsumer implements EventConsumer{

    @Autowired
    private EventHandlers eventHandlers;

    @KafkaListener(topics = "AccountOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload AccountOpenedEvent event, Acknowledgment ack) {
        eventHandlers.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "FundDepositedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload FundDepositedEvent event, Acknowledgment ack) {
        eventHandlers.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "FundWithDrownEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload FundWithDrownEvent event, Acknowledgment ack) {
        eventHandlers.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "AccountClosedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload AccountClosedEvent event, Acknowledgment ack) {
        eventHandlers.on(event);
        ack.acknowledge();
    }
}
