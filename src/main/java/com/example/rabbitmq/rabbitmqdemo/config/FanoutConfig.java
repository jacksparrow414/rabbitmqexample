package com.example.rabbitmq.rabbitmqdemo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 广播发送Exchange.<br/>
 * 当消息被发送到FanoutExchange时，绑定在此Exchange上的所有Queue的所有消费者都会消费一样的消息.也就是说，一条消息可以被多个消费者消费<br/>
 * 生产者、消费者之间不再通过Queue进行消息的传递.而是通过Exchange,具体消息的传递如下:<br/>
 * 第一步:<br/>
 * <pre>
 *  ------------                                           -----------
 *  | Producer |--发布消息，通过routingKey发送具体Exchange--> | Exchange |
 *  ------------                                           -----------
 *  </pre>
 * 第二步:<br/>
 * <pre>
 *  -----------                                                       -----------
 *  | Exchange |--通过binding，找到绑定在此Exchange上的所有Queue并发送---> | Queue 1 |
 *  |          |                                                      -----------
 *  |          |
 *  |          |                                                      -----------
 *  |          |                                                      | Queue 2 |
 *  -----------                                                       -----------
 * </pre>
 * 第三步:
 * <pre>
 * -----------                -------------
 * | Queue 1 |---消费消息1---->| Consumer 1 |
 * -----------                -------------
 *
 * -----------                -------------
 * | Queue 2 |---消费消息1---->| Consumer 2 |
 * -----------                -------------
 * </pre>
 *
 *
 *
 *
 *
 * Exchange有多种类型，这里是Fanout，就是<b>广播给绑定在此Exchange上的所有队列</b>.<br/>
 * Producer和Exchange直接通过routingKey来配置.前面的基本使用和工作队列都是通过指定Queue的名字来进行联系的<br/>
 * Queue和Exchange的绑定关系，通过配置Binding来手动配置的，将某些队列绑到一个或者几个Exchange上.
 * @author jacksparrow414
 * @date 2020/12/13
 */
@Configuration
public class FanoutConfig {
    
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout");
    }
    
    @Bean
    public Queue firstFanoutQueue() {
        return new Queue("firstFanout");
    }
    
    @Bean
    public Queue secondFanoutQueue() {
        return new Queue("secondFanout");
    }
    
    @Bean
    public Binding firstFanoutBinding() {
       return BindingBuilder.bind(firstFanoutQueue()).to(fanoutExchange());
    }
    
    @Bean
    public Binding secondFanoutBinding() {
        return BindingBuilder.bind(secondFanoutQueue()).to(fanoutExchange());
    }
}
