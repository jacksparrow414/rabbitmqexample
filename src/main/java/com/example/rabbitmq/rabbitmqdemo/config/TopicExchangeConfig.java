package com.example.rabbitmq.rabbitmqdemo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TopicExchange工作原理和{@link DirectExchangeConfig}、{@link FanoutExchangeConfig}基本一致.<br/>
 * 对于TopicExchange的routingKey，是模式匹配，可以简单理解为模糊匹配。
 * 一个Queue和TopicExchange之间的bindingKey,只要有满足的routingKey，TopicExchange就会吧该消息转发给对应bindingKey的Queue<br/>
 * routingKey的规则也不再像{@link DirectExchangeConfig}那样固定为某个字符串，而是
 * 以 . 分隔的一些词组成的一个规则，<a href="https://www.rabbitmq.com/tutorials/tutorial-five-java.html">关于TopicExchange的routingKey的说明</a>
 * 对于词组有两种特殊的占位符*和#<br/>
 * *是具体的某个词<b>一个*只能代表一个词</b>。例如: *.first.* 这种只能匹配xxx.first.xxx三个字词的，如果三个以上xxx.xxx.first.xxx、xxx.first、first.xxx、则都不能匹配</br>
 * #不仅可以表示一个词，还可以表示多个，或者没有<br/>
 * 当一条消息中携带的routingKey为：
 * routingKey=message.first.second.Topic
 * 此routingKey只满足任何{@link TopicExchangeConfig#fourthTopicBinding()}<br/>
 *
 * <b>routingKey的最大长度为255个byte</b>
 * @author jacksparrow414
 * @date 2020/12/14 14:14
 */
@Configuration
public class TopicExchangeConfig {

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topic");
    }

    @Bean
    public Queue firstTopicQueue() {
        return new Queue("firstTopic");
    }

    @Bean
    public Queue secondTopicQueue() {
        return new Queue("secondTopic");
    }

    @Bean
    public Queue thirdTopicQueue() {
        return new Queue("thirdTopic");
    }

    @Bean
    public Binding firstTopicBinding() {
        return BindingBuilder.bind(firstTopicQueue()).to(topicExchange()).with("*.first.*");
    }

    @Bean
    public Binding secondTopicBinding() {
        return BindingBuilder.bind(secondTopicQueue()).to(topicExchange()).with("*.second.*");
    }

    @Bean
    public Binding thirdTopicBinding() {
        return BindingBuilder.bind(thirdTopicQueue()).to(topicExchange()).with("*.*.Topic");
    }

    @Bean
    public Binding fourthTopicBinding() {
        return BindingBuilder.bind(thirdTopicQueue()).to(topicExchange()).with("message.#");
    }
}
