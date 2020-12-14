package com.example.rabbitmq.rabbitmqdemo.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jacksparrow414
 * @date 2020/12/14 14:38
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public final class TopicExampleTest {

    @Autowired
    private TopicExample topicExample;

    @Test
    public void assertTopicExchangeUse() {
        topicExample.topicExchangeUse();
    }
}