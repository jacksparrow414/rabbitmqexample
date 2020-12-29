package com.example.rabbitmq.rabbitmqdemo.config.inner.mq;

import java.util.Calendar;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.CacheMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.ConfirmType;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 如果要想使用publish-confirm模式则需要使用CachingConnectionFactory<br/>
 * <a href="https://docs.spring.io/spring-amqp/docs/current/reference/html/#template-confirms">官方文档</a><br/>
 * <a href="https://docs.spring.io/spring-amqp/docs/current/reference/html/#cf-pub-conf-ret">官方文档</a>
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
@Slf4j
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
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public RabbitTemplate publishConfirmRabbitTemplate() {
        RabbitTemplate result = new RabbitTemplate(publishConnectionFactory());
        result.setMessageConverter(messageConverter());
        result.setBeforePublishPostProcessors(message -> {
            // 设置默认Content-Type
            if (message.getMessageProperties().getContentType() == null) {
                message.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
            }
            if (message.getMessageProperties().getTimestamp() == null) {
                message.getMessageProperties().setTimestamp(Calendar.getInstance().getTime());
            }
            return message;
        });
        // 第一步：判断Producer -> Exchange 是否有问题
        // 要想使用correlationData，则发送时要设置该值，否则是null，设置方法见{@link com.example.rabbitmq.rabbitmqdemo.producer.TopicProducer.sendTopicMessage}
        result.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("能够被Rabbit MQ Server的exchange接收到......");
            }else {
                log.info("在Rabbit MQ Server上找不到对应的exchange......原始信息id是{}, 原始信息是{}, 原因是{}" ,
                    Objects.requireNonNull(correlationData).getId(),
                    // 获取到body体的字节数组，直接只用 new String(byte[])构造方法，将字节数组转为字符串
                    new String(Objects.requireNonNull(correlationData.getReturnedMessage()).getBody()),
                    cause);
            }
        });
        // 第二步：判断Exchange -> Queue 是否有问题
        // 设置为true，在exchange找不到queue的时候才会回调
        // 官方文档说明：https://docs.spring.io/spring-amqp/docs/current/reference/html/#template-confirms
        result.setMandatory(true);
        result.setReturnsCallback(returned -> {
            log.info("找到了exchange，但是没有被路由到正确的queue....routingKey是:{}, 原始信息是{}", returned.getRoutingKey(), new String(returned.getMessage().getBody()));
        });
        return result;
    }
}
