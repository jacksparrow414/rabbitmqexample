package com.example.rabbitmq.rabbitmqdemo.example;

import com.example.rabbitmq.rabbitmqdemo.producer.TopicProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jacksparrow414
 * @date 2020/12/14 14:37
 */
@Slf4j
@Component
public class TopicExample {

    @Autowired
    private TopicProducer topicProducer;

    public void topicExchangeUse() {
        topicProducer.sendTopicMessage();
    }
}
