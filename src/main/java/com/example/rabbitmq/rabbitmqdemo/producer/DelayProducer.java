package com.example.rabbitmq.rabbitmqdemo.producer;

import com.example.rabbitmq.rabbitmqdemo.utils.MessageUtils;
import java.time.LocalDateTime;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author jacksparrow414
 * @date 2021/2/13
 */
@Slf4j
@Component
@EnableScheduling
public class DelayProducer {
    
    @Resource(name = "rabbitTemplate")
    private RabbitTemplate rabbitTemplate;
    
    @Resource(name = "delayTopicExchange")
    private TopicExchange delayTopicExchange;
    
    @Scheduled(fixedDelay = 60000)
    public void sendDelayExchange() {
        Message delayMessage = MessageUtils.buildStringMessage("this is delay message", MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        // 发送消息时，设置消息为延迟消息
        delayMessage.getMessageProperties().setDelay(10000);
        log.info("发送延迟消息{},时间为{}", new String(delayMessage.getBody()), LocalDateTime.now().toString());
        rabbitTemplate.convertAndSend(delayTopicExchange.getName(), "message.delay", delayMessage);
    }
}
