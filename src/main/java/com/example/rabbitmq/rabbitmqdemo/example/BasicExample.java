package com.example.rabbitmq.rabbitmqdemo.example;

import com.example.rabbitmq.rabbitmqdemo.producer.BasicProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author jacksparrow414
 * @date 2020/12/4 10:29
 */
@Component
public class BasicExample {

   @Autowired
   private BasicProducer basicProducer;

   /**
    * 基本使用.
    * @author jacksparrow414
    * @return void
    */
   public void basicUse() {
       basicProducer.sendBasicMessage(LocalDateTime.now());
   }
}
