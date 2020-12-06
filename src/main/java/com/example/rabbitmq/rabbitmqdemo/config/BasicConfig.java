package com.example.rabbitmq.rabbitmqdemo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定义一个Queue,注意这里的Queue是{@link org.springframework.amqp.core.Queue}<br/>
 * 这里最简单的消息队列是下面这样的<br/>
 * <pre>
 * ------------                     ------------                     -----------
 * | Producer |  -----发布消息-----> |   Queue   |  -----消费消息----> | Consumer |
 * ------------                     ------------                     ----------- </pre>
 * @author jacksparrow414
 * @date 2020/12/4 10:15
 */
@Configuration
public class BasicConfig {

    @Bean
    public Queue basicQueue() {
        return new Queue("basic");
    }
}
