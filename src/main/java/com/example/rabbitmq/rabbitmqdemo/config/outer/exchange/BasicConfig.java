package com.example.rabbitmq.rabbitmqdemo.config.outer.exchange;

import org.springframework.context.annotation.Configuration;

/**
 * 定义一个Queue,注意这里的Queue是{@link org.springframework.amqp.core.Queue}<br/>
 * 这里最简单的消息队列是下面这样的,生成者生产消息，并通过指定Queue的名字，发送到具体的Queue,消费者从指定的Queue中消费消息<br/>
 * <pre>
 * ------------                     ------------                     -----------
 * | Producer |  -----发布消息-----> |   Queue   |  -----消费消息----> | Consumer |
 * ------------                     ------------                     ----------- </pre>
 * @author jacksparrow414
 * @date 2020/12/4 10:15
 */
@Configuration
public class BasicConfig {


}
