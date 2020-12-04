package com.example.rabbitmq.rabbitmqdemo.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jacksparrow414
 * @date 2020/12/4 10:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public final class BasicExampleTest {

    @Autowired
    private BasicExample basicExample;

    @Test
    public void assertBasicUse() {
        basicExample.basicUse();
    }
}