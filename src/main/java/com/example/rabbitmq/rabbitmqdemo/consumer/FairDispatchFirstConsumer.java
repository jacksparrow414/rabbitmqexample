package com.example.rabbitmq.rabbitmqdemo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 公平派发的第一个消费者.
 * @author jacksparrow414
 * @date 2020/12/4 10:13
 */
@Component
@Slf4j
@RabbitListener(queues = "fairDispatch")
public class FairDispatchFirstConsumer {
    
    private static final String FIRST_CONSUMER = "My name is first-fair-dispatch, I receive a message is: ";
    /**
     * 接收消息.
     * @author jacksparrow414
     * @param receivedMessage 接收到的消息
     * @return void
     */
    @RabbitHandler
    public void receiveFairDispatchMessage(String receivedMessage) {
        log.info(FIRST_CONSUMER+receivedMessage);
    }
}
