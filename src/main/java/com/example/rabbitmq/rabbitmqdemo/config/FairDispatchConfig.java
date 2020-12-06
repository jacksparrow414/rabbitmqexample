package com.example.rabbitmq.rabbitmqdemo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <a href="https://www.rabbitmq.com/tutorials/tutorial-two-spring-amqp.html">工作队列文档(包含公平派发和轮询派发)</a><br/>
 * 公平派发的队列配置.<br/>
 * 工作队列比起基本队列{@link BasicConfig},工作队列可以有多个消费者,并且队列中的多个消息会分给多个消费者.
 * 多个消费者之间同一时间不会消费同一条消息.<br/>
 * <pre>
 * -----------                 --------                             ----------
 * | Producer |----发布消息---->| Work |  ----消费 消息1、消息3------> |Consumer1|
 * -----------                 |      |                             ----------
 *                             |      |
 *                             | Queue|                             ----------
 *                             |      |  ----消费 消息2、消息4------> |Consumer2|
 *                             --------                             ----------</pre>
 * RabbitMQ在多个消费者之间采用的round-robin(轮询)的方式.
 * <ul>
 * <li> 第1条消息给消费者1
 * <li> 第二条消息给消费者2
 * <li> 第三条消息给消费者1
 * <li> 第四条消息给消费者2
 * </ul>
 *
 * 但是这样有个问题,当消费者2消费消息很慢时，依然按照轮询的方式，会导致很多消息等待消费者2处理<br/>
 * Spring AMQP则实现了<b>fair dispatch(公平派发)</b>.
 * 即根据各个消费者的消费能力来分发消息,当消费者2因消费消息时间过长,那么消费能力强的消费者1则会被分到更多的消息.<br/>
 * 但是在这里有个小小的坑，Spring AMQP虽然默认是公平派发的，它有一个prefetch的设置,意思为预取，
 * 就是说当消息队列分发消息时，跟根据预取的值，给当前消费者分配消息.prefetch的值默认为250.
 * 可以看到，如果我们是公平派发，而且默认值是250，而且是自动确认
 * Spring AMQP会认为每个消费者都可以预取250个消息，当Unacked的数量未达到250时,会被认为还可以消费消息，
 * 而实际上我们消费者2第消费的第一个消息时就阻塞了,此时造成的现象就是明明消费者2已经阻塞了，你怎么还有消息等着我消费？
 * 这明显和你说的公平派发不一致，这明显就是轮询.
 * 所以，针对这种情况，<b>我们设置prefetch=1或者2或者很低的值，来验证Spring AMQP是否为公平派发.</b>
 * 观察RabbitMQ的控制台会发现，当
 * Unacked的值达到了我们设置的prefetch的值之后，后面的消息则会被消费者1消费<br/>
 * <b>而网上说的什么prefetch=1开启公平派发其实大错特错</b>
 * 人家本身就是公平派发的，只不过预估了你实际的处理速度
 * @author jacksparrow414
 * @date 2020/12/6
 */
@Configuration
public class FairDispatchConfig {
    
    @Bean
    public Queue fairDispatchQueue() {
        return new Queue("fairDispatch");
    }
}
