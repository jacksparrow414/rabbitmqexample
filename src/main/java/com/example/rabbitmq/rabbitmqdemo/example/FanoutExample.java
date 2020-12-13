package com.example.rabbitmq.rabbitmqdemo.example;

import com.example.rabbitmq.rabbitmqdemo.producer.FanoutProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jacksparrow414
 * @date 2020/12/13
 */
@Component
public class FanoutExample {
    
    @Autowired
    private FanoutProducer fanoutProducer;
    
    public void fanoutExchangeUse() {
        fanoutProducer.sendFanoutMessage();
    }
}
