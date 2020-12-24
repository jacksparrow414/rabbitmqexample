package com.example.rabbitmq.rabbitmqdemo.producer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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

    @Resource(name = "publishConfirmRabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TopicExchange topicExchange;

    @PostConstruct
    public void sendTopicMessage() {
        // 可以手动设置Exchange名字,但是全局公用一个rabbitTemplate，不建议这样设置，
        // 这样设置，除非所有用到的地方发送之前都设置一下当前的exchange，否则，这次发送消息完毕，exchange不改变，则会影响其他的消息发送
        // rabbitTemplate.setExchange(topicExchange.getName());
        rabbitTemplate.convertAndSend(topicExchange.getName(),"message.first.Topic", "this is send first topic message");
        rabbitTemplate.convertAndSend(topicExchange.getName(),"discard.message.first.Topic", "this is send first discard topic message");
        rabbitTemplate.convertAndSend(topicExchange.getName(), "first.discard", "this is send the other first discard topic message");
        // 2020-12-24更新：要想恢复正常，删掉wrong.queue.和wrongExchange字符串
        rabbitTemplate.convertAndSend(topicExchange.getName(), "wrong.queue.message.second.Topic", "this is send second topic message");
        rabbitTemplate.convertAndSend(topicExchange.getName()+"wrongExchange", "message.first.second.Topic", "this is send multi topic message");
    }
}
