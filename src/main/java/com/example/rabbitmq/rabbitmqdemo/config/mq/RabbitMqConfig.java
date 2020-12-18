package com.example.rabbitmq.rabbitmqdemo.config.mq;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 具体类型的ConnectionFactory见<a href="https://docs.spring.io/spring-amqp/docs/current/reference/html/#connections">Spring AMQP文档</a>
 * @author jacksparrow414
 * @date 2020/12/16
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 官方推荐使用{@link PooledChannelConnectionFactory}来管理Channel连接.
     */
    @Bean
    public org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        PooledChannelConnectionFactory result = new PooledChannelConnectionFactory(connectionFactory);
        result.setHost("localhost");
        result.setPort(5672);
        result.setUsername("dhb");
        result.setPassword("123456");
        result.setVirtualHost("dhb");
        result.setConnectionNameStrategy(factory -> "producer-connection");
        // TODO 对象池的设置
        // 使用Apache Common Pool2,一共有两个对象池
        result.setPoolConfigurer((pool, tx) -> {
            if (tx) {
                // 用于管理事务的对象池
                pool.setMaxTotal(2);
            }else {
                // 非事务的对象池,默认最大为8
                pool.setMaxTotal(8);
            }
        });
        return result;
    }
    
    @Bean
    public AmqpAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }
    
    /**
     * 默认MessageConverter是{@link org.springframework.amqp.support.converter.SimpleMessageConverter}
     * @return
     */
    @Bean(name = "rabbitTemplate")
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate reuslt = new RabbitTemplate(connectionFactory());
//        reuslt.addListener();
        
        reuslt.setMessageConverter(new Jackson2JsonMessageConverter());
//        reuslt.addBeforePublishPostProcessors();
//        reuslt.addAfterReceivePostProcessors();
//        reuslt.setConfirmCallback();
        return reuslt;
    }
}
