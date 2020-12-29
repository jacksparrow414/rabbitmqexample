package com.example.rabbitmq.rabbitmqdemo.config.outer.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * @author jacksparrow414
 * @date 2020/12/29
 */
@Data
public class SimpleDirectEntity implements Serializable {
    
    private Long id;
    
    private String name;
    
    private Boolean simple;
}
