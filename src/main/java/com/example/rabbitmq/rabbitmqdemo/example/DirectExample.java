package com.example.rabbitmq.rabbitmqdemo.example;

import com.example.rabbitmq.rabbitmqdemo.producer.DirectProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jacksparrow414
 * @date 2020/12/13
 */
@Component
public class DirectExample {
    
    @Autowired
    private DirectProducer directProducer;
    
    public void directExchangeUse() {
        directProducer.sendDirectMessage();
        directProducer.sendDirectConvertMessage();
        directProducer.sendDirectPoJoMessage();
    }
}
