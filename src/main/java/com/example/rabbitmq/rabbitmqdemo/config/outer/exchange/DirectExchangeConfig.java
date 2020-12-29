package com.example.rabbitmq.rabbitmqdemo.config.outer.exchange;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DirectExchange的工作原理和{@link FanoutExchangeConfig}基本一致.
 * 只不过该Exchange在分发消息时，会根据Producer携带的routingKey寻找当前绑定在该Exchange上的Queue的bindingKey
 * 如果routingKey=bindKey，则将该消息分发到对应的bindKey所在的Queue中，由该Queue上监听的消费者进行消费消息.
 * 并且DirectExchange是完全匹配的工作模式，也就是routingKey和bindKey必须相等，消息才会被发送对应的Queue上.
 * bindingKey通过BindingBuilder.with()指定.
 * routingKey由生产者发送消息时指定{@link com.example.rabbitmq.rabbitmqdemo.producer.DirectProducer}
 * @author jacksparrow414
 * @date 2020/12/13
 */
@Configuration
public class DirectExchangeConfig {
    
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct");
    }
    
}
