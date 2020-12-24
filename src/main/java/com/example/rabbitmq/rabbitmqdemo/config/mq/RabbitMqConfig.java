package com.example.rabbitmq.rabbitmqdemo.config.mq;

import com.rabbitmq.client.ConnectionFactory;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CompositeChannelListener;
import org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 具体类型的ConnectionFactory见<a href="https://docs.spring.io/spring-amqp/docs/current/reference/html/#connections">Spring AMQP文档</a>
 * @author jacksparrow414
 * @date 2020/12/16
 */
@Slf4j
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
        // 设置连接的名字
        result.setConnectionNameStrategy(factory -> "producer-connection");
        // TODO 对象池的设置
        // 使用Apache Common Pool2,一共有两个对象池
        result.setPoolConfigurer((pool, tx) -> {
            if (tx) {
                // 用于管理事务的对象池
                pool.setMaxTotal(8);
            } else {
                // 非事务的对象池,默认最大为8
                pool.setMaxTotal(8);
                // 以下四行能够使驱逐规则生效，至于为什么生效见{@link org.apache.commons.pool2.impl.DefaultEvictionPolicy}代码
                // 不要开启驱逐规则
                //                pool.setMaxIdle(2);
                //                pool.setMinIdle(2);
                //                pool.setMinEvictableIdleTimeMillis(20000);
                //                pool.setSoftMinEvictableIdleTimeMillis(15000);
                // 驱逐线程执行扫描任务周期，设置为10秒一次
                //                pool.setTimeBetweenEvictionRunsMillis(10000);
                //                pool.setNumTestsPerEvictionRun(5);
            }
        });
        result.setChannelListeners(Collections.singletonList(new CompositeChannelListener()));
        return result;
    }
    
    @Bean
    public AmqpAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }
    
    /**
     * 默认MessageConverter是{@link org.springframework.amqp.support.converter.SimpleMessageConverter}
     *
     * @return
     */
    @Bean(name = "rabbitTemplate")
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate reuslt = new RabbitTemplate(connectionFactory());
        reuslt.setBeforePublishPostProcessors(message -> {
            System.out.println("发消息之前处理动作--->");
            return message;
        });
        return reuslt;
    }
}
