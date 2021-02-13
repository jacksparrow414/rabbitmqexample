package com.example.rabbitmq.rabbitmqdemo.example;

import com.example.rabbitmq.rabbitmqdemo.producer.DelayProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jacksparrow414
 * @date 2021/2/13
 */
@Component
public class DelayExample {
    
    @Autowired
    private DelayProducer delayProducer;
    
    public void delayUseExample() {
        delayProducer.sendDelayExchange();
    }
}
