package com.example.rabbitmq.rabbitmqdemo.producer;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * 公平派发的生产者.
 * @author jacksparrow414
 * @date 2020/12/6
 */
@Slf4j
@Component
@EnableScheduling
public class FairDispatchProducer {
    
    private static final String FAIR_DISPATCH_MESSAGE = "This is a fair dispatch message";
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    /**
     * 定时5秒发送发送消息.
     */
    //@Scheduled(fixedDelay = 5000, initialDelay = 3000)
    public void sendFairDispatchMessage() {
        rabbitTemplate.convertAndSend("fairDispatch", FAIR_DISPATCH_MESSAGE + LocalDateTime.now());
    }
}
