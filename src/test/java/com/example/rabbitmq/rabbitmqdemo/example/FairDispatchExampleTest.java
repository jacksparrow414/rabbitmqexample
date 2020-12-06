package com.example.rabbitmq.rabbitmqdemo.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author jacksparrow414
 * @date 2020/12/6
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public final class FairDispatchExampleTest {
    
    @Autowired
    private FairDispatchExample fairDispatchExample;
    
    @Test
    public void assertFairDispatchUse() {
        fairDispatchExample.fairDispatchUse();
    }
}