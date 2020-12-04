package com.example.rabbitmq.rabbitmqdemo.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author jacksparrow414
 * @date 2020/12/4 10:06
 */
@Slf4j
@Component
public class BasicProducer {

    private static final String BASIC_MESSAGE = "This is a basic message ";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    /**
     * 发送消息.
     * @author jacksparrow414
     * @param  localDateTime 时间
     * @return void
     */
    public void sendBasicMessage(LocalDateTime localDateTime) {
        rabbitTemplate.convertAndSend(queue.getName(), BASIC_MESSAGE + localDateTime.toString());
    }
}
