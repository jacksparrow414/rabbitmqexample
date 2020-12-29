package com.example.rabbitmq.rabbitmqdemo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;

/**
 * 消息转换工具.用来生成Message对象.
 * @author jacksparrow414
 * @date 2020/12/29
 */
public class MessageUtils {
    
    /**
     * 消息体为字符串的消息构建.
     * @param messageStr 消息字符串
     * @param contentType contentType
     * @return org.springframework.amqp.core.Message Message对象
     */
    public static Message buildStringMessage(String messageStr, String contentType) {
        MessageProperties messageProperties = MessagePropertiesBuilder.newInstance().setContentType(contentType).build();
        return new Message(messageStr.getBytes(), messageProperties);
    }
    
    /**
     * 消息体为POJO的消息构建
     * @param messagePoJo
     * @param <T>
     * @return org.springframework.amqp.core.Message Message对象
     */
    @SneakyThrows
    public static <T> Message buildPoJoMessage(T messagePoJo) {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(messagePoJo);
       return MessageBuilder.withBody(bytes).setContentType(MessageProperties.CONTENT_TYPE_JSON).build();
    }
}
