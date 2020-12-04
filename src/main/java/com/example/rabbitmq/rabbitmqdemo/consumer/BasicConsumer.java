package com.example.rabbitmq.rabbitmqdemo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author jacksparrow414
 * @date 2020/12/4 10:13
 */
@Component
@Slf4j
@RabbitListener(queues = "basic")
public class BasicConsumer {

    /**
     * 接收消息.
     * @author jacksparrow414
     * @param receivedMessage 接收到的消息
     * @return void
     */
    @RabbitHandler
    public void receiveBasicMessage(String receivedMessage) {
        System.out.println(receivedMessage);
    }
}
