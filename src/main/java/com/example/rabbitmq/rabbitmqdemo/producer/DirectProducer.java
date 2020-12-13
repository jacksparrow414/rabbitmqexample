package com.example.rabbitmq.rabbitmqdemo.producer;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 完全匹配的Exchange的生产者发送消息.
 * 在发送时指定routingKey和对应的bindingKey做匹配.
 * bindingKey见{@link com.example.rabbitmq.rabbitmqdemo.config.DirectExchangeConfig}
 * @author jacksparrow414
 * @date 2020/12/13
 */
@Component
public class DirectProducer {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private DirectExchange directExchange;
    
    public void sendDirectMessage() {
        rabbitTemplate.convertAndSend(directExchange.getName(), "first", "this is send first message");
        rabbitTemplate.convertAndSend(directExchange.getName(), "second", "this is send second message");
    }
}
