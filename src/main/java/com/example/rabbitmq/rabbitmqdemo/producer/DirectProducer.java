package com.example.rabbitmq.rabbitmqdemo.producer;

import com.example.rabbitmq.rabbitmqdemo.config.outer.entity.SimpleDirectEntity;
import com.example.rabbitmq.rabbitmqdemo.config.outer.exchange.DirectExchangeConfig;
import com.example.rabbitmq.rabbitmqdemo.utils.MessageUtils;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 【完全匹配】的Exchange的生产者发送消息.
 * 在发送时指定routingKey和对应的bindingKey做匹配.
 * bindingKey见{@link DirectExchangeConfig}
 * @author jacksparrow414
 * @date 2020/12/13
 */
@Component
public class DirectProducer {
    
    @Resource(name = "rabbitTemplate")
    private RabbitTemplate rabbitTemplate;
    
    @Resource(name = "directExchange")
    private DirectExchange directExchange;
    
    
    public void sendDirectMessage() {
        rabbitTemplate.convertAndSend(directExchange.getName(), "first", "this is send first message");
        rabbitTemplate.convertAndSend(directExchange.getName(), "second", "this is send second message");
    }
    
    /**
     * 发送消息，消息主体时String.
     */
    @PostConstruct
    public void sendDirectConvertMessage() {
        Message message = MessageUtils.buildStringMessage("this is message convert", MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        // 直接使用send，不再使用convertAndSend
        rabbitTemplate.send(directExchange.getName(), "str", message);
    }
    
    /**
     * 发送消息，消息主体是POJO.
     */
    @PostConstruct
    public void sendDirectPoJoMessage() {
        Random random = new Random(100);
        SimpleDirectEntity simpleDirectEntity = new SimpleDirectEntity();
        simpleDirectEntity.setId(random.nextLong());
        simpleDirectEntity.setName("pojo");
        simpleDirectEntity.setSimple(true);
        Message message = MessageUtils.buildPoJoMessage(simpleDirectEntity);
        rabbitTemplate.send(directExchange.getName(), "pojo" , message);
    }
}
