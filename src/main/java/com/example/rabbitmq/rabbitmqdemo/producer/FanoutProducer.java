package com.example.rabbitmq.rabbitmqdemo.producer;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 发往FanoutExchange的生产者.<br/>
 * 在FanoutExchange中，routingKey是不需要的，所以设置为空字符串.因为会被忽略掉.详见<a href="https://www.rabbitmq.com/tutorials/tutorial-three-spring-amqp.html">官方文档中的Putting it all together部分</a>
 * @author jacksparrow414
 * @date 2020/12/13
 */
@Slf4j
@Component
@EnableScheduling
public class FanoutProducer {
    
    private static final String FANOUT_MESSAGE = "This is a fanout message";
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private FanoutExchange fanoutExchange;
    
    @Scheduled(fixedDelay = 5000, initialDelay = 5000)
    public void sendFanoutMessage() {
        rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", FANOUT_MESSAGE + LocalDateTime.now().toString());
    }
}
