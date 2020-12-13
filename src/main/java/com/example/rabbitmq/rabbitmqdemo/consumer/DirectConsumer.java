package com.example.rabbitmq.rabbitmqdemo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author jacksparrow414
 * @date 2020/12/13
 */
@Component
@Slf4j
public class DirectConsumer {
    
    @RabbitListener(queues = "firstDirect")
    @RabbitHandler
    public void receiveFirstDirectQueueMessage(String firstQueueMessage) {
        log.info("This is firstDirectQueue received message: " + firstQueueMessage);
    }
    
    @RabbitListener(queues = "secondDirect")
    @RabbitHandler
    public void  receiveSecondDirectQueueMessage(String secondQueueMessage) {
        log.info("This is secondDirectQueue received message: " + secondQueueMessage);
    }
}
