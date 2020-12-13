package com.example.rabbitmq.rabbitmqdemo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 关于@RabbitListener注解的位置，不仅可以放在类上，也可以放在具体方法上，这样可以在一个勒种处理多个不同的queues<br/>
 * 和{@link FairDispatchFirstConsumer}、{@link BasicConsumer}不同，他们是一个类只放同一个queue的处理。
 * 这两种方式可以根据实际情况选择最适合的
 * @author jacksparrow414
 * @date 2020/12/13
 */
@Slf4j
@Component
public class FanoutConsumer {
    
    @RabbitListener(queues = "firstFanout")
    @RabbitHandler
    public void receiveFirstFanoutQueueMessage(String firstFanoutQueueMessage) {
        log.info("This is firstFanoutQueue received message: "+firstFanoutQueueMessage);
    }
    
    @RabbitListener(queues = "secondFanout")
    @RabbitHandler
    public void receiveSecondFanoutQueueMessage(String secondFanoutQueueMessage) {
        log.info("This is secondFanoutQueue received message: "+secondFanoutQueueMessage);
    }
}
