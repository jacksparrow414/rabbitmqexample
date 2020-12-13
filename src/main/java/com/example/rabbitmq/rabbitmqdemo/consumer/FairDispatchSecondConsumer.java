package com.example.rabbitmq.rabbitmqdemo.consumer;

import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 公平派发的第二个消费者.
 * @author jacksparrow414
 * @date 2020/12/4 10:13
 */
@Component
@Slf4j
@RabbitListener(queues = "fairDispatch")
public class FairDispatchSecondConsumer {

    private static final String SECOND_CONSUMER = "My name is second-fair-dispatch, I receive a message is: ";
    
    /**
     * 接收消息.
     * @author jacksparrow414
     * @param receivedMessage 接收到的消息
     * @return void
     */
    @RabbitHandler
    @SneakyThrows
    public void receiveFairDispatchMessage(String receivedMessage) {
        // 休眠1分钟,用来模拟该消费者执行耗时比较长的情况
        TimeUnit.MINUTES.sleep(1);
        log.info(SECOND_CONSUMER + receivedMessage);
    }
}
