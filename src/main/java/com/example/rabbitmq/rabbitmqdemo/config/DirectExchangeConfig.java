package com.example.rabbitmq.rabbitmqdemo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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
    
    @Bean
    public Queue firstDirectQueue() {
        return new Queue("firstDirect");
    }
    
    @Bean
    public Queue secondDirectQueue() {
        return new Queue("secondDirect");
    }
    
    @Bean
    public Binding firstDirectBinding() {
        return BindingBuilder.bind(firstDirectQueue()).to(directExchange()).with("first");
    }
    
    @Bean
    public Binding secondDirectBinding() {
        return BindingBuilder.bind(secondDirectQueue()).to(directExchange()).with("second");
    }
}
