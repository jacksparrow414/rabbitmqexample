package com.example.rabbitmq.rabbitmqdemo.config.outer.exchange;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息延迟的经典使用方法是TTL(Time-To-Live)+死信队列(具体实现可自行Google)</br>
 * 即：发送消息设置消息过期时间，当消息过期之后，会流入 死信队列，此时，监听死信队列的消费者会进行消费。
 * 这里使用另外一种方式：两个必要条件
 * 1、将队列设置为延迟队列，
 * 2、发送消息时为消息设置延迟时间
 * <a href="https://docs.spring.io/spring-amqp/docs/current/reference/html/#delayed-message-exchange">spring AMQP官方文档</a>
 * <a href="https://www.rabbitmq.com/blog/2015/04/16/scheduling-messages-with-rabbitmq/">rabbit MQ官方文档</a>
 * @author jacksparrow414
 * @date 2021/2/13
 */
@Configuration
public class DelayExchangeConfig {
    
    @Bean
    public TopicExchange delayTopicExchange() {
        TopicExchange result = new TopicExchange("delay");
          result.setDelayed(true);
        return result;
    }
}
