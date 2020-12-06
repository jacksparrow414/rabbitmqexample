package com.example.rabbitmq.rabbitmqdemo.producer;

import com.example.rabbitmq.rabbitmqdemo.config.BasicConfig;
import java.time.LocalDateTime;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 最基本的消息队列中Producer发送消息到消息队列.<br/>
 * 通过@Resource注解引入固定的消息队列{@link BasicConfig#basicQueue()}
 * @author jacksparrow414
 * @date 2020/12/4 10:06
 */
@Slf4j
@Component
public class BasicProducer {

    private static final String BASIC_MESSAGE = "This is a basic message ";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Resource(name = "basicQueue")
    private Queue basicQueue;

    /**
     * 发送消息.
     * @author jacksparrow414
     * @param  localDateTime 时间
     * @return void
     */
    public void sendBasicMessage(LocalDateTime localDateTime) {
        rabbitTemplate.convertAndSend(basicQueue.getName(), BASIC_MESSAGE + localDateTime.toString());
    }
}
