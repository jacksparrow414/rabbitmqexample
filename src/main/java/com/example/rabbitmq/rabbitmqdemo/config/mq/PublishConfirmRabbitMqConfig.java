package com.example.rabbitmq.rabbitmqdemo.config.mq;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.CacheMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.ConfirmType;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 如果要想使用publish-confirm模式则需要使用CachingConnectionFactory<br/>
 * <a href="https://docs.spring.io/spring-amqp/docs/current/reference/html/#template-confirms">官方文档</a><br/>
 * 开启条件：
 * <pre>
 * 1、PublisherReturns = true
 * 2、PublisherConfirmType = ConfirmType.CORRELATED;
 * 3、CacheMode = CacheMode.CONNECTION</pre>
 *
 * 生产者端要保证消息的可靠投递：
 * <a href="https://docs.spring.io/spring-amqp/docs/current/reference/html/#publishing-is-async">如何判断消息的成功失败</a><br/>
 * <pre>
 * 1、Producer -> Exchange 可以找到对应的Exchange接收，不会出现找不动对应Exchange的情况
 * 2、Exchange -> Queue 能够根据Producer携带的routingKey，根据已有binding找到对应的Queue,不会出现Exchange找不到对应Queue的情况
 * </pre>
 * @author jacksparrow414
 * @date 2020/12/24
 */
@Configuration
public class PublishConfirmRabbitMqConfig {
    
    
    @Bean
    public ConnectionFactory publishConnectionFactory() {
        CachingConnectionFactory result = new CachingConnectionFactory();
        result.setConnectionNameStrategy(connectionFactory -> "publishConnection");
        result.setHost("localhost");
        result.setPort(5672);
        result.setUsername("dhb");
        result.setPassword("123456");
        result.setVirtualHost("dhb");
        result.setCacheMode(CacheMode.CONNECTION);
        result.setPublisherReturns(true);
        result.setPublisherConfirmType(ConfirmType.CORRELATED);
        return result;
    }
    
    @Bean
    public RabbitTemplate publishConfirmRabbitTemplate() {
        RabbitTemplate result = new RabbitTemplate(publishConnectionFactory());
        // 第一步：判断Producer -> Exchange 是否有问题
        result.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("能够被Rabbit MQ Server的exchange接收到......");
            }else {
                System.out.println("在Rabbit MQ Server上找不到对应的exchange......" + cause);
            }
        });
        // 第二步：判断Exchange -> Queue 是否有问题
        
        // 设置为true，在exchange找不到queue的时候才会回调
        // 官方文档说明：https://docs.spring.io/spring-amqp/docs/current/reference/html/#template-confirms
        result.setMandatory(true);
        result.setReturnsCallback(returned -> {
            System.out.println("找到了exchange，但是没有被路由到正确的queue....routingKey是:" + returned.getRoutingKey());
        });
        return result;
    }
}
