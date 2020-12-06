package com.example.rabbitmq.rabbitmqdemo.example;

import com.example.rabbitmq.rabbitmqdemo.producer.FairDispatchProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jacksparrow414
 * @date 2020/12/6
 */
@Component
public class FairDispatchExample {
    
    @Autowired
    private FairDispatchProducer fairDispatchProducer;
    
    /**
     * Spring AMQP对于一个队列,多个消费者的情况,默认规则是公平分配
     */
    public void fairDispatchUse() {
        fairDispatchProducer.sendFairDispatchMessage();
    }
}
