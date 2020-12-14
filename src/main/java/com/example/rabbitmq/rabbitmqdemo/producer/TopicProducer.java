package com.example.rabbitmq.rabbitmqdemo.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jacksparrow414
 * @date 2020/12/14 14:28
 */
@Slf4j
@Component
public class TopicProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TopicExchange topicExchange;

    public void sendTopicMessage() {
        // 可以手动设置Exchange名字
        rabbitTemplate.setExchange(topicExchange.getName());
        rabbitTemplate.convertAndSend("message.first.Topic", "this is send first topic message");
        rabbitTemplate.convertAndSend("discard.message.first.Topic", "this is send first discard topic message");
        rabbitTemplate.convertAndSend("first.discard", "this is send the other first discard topic message");
        rabbitTemplate.convertAndSend("message.second.Topic", "this is send second topic message");
        rabbitTemplate.convertAndSend("message.first.second.Topic", "this is send multi topic message");
    }
}
