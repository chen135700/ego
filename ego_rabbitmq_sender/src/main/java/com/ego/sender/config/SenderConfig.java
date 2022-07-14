package com.ego.sender.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class SenderConfig {

    @Value("${ego.rabbitmq.content.queueName}")
    private String contentQueue;

    @Bean
    public Queue queue() {
        return new Queue("content");
    }

    @Bean
    public DirectExchange directExchange(){
        return  new DirectExchange("amq.direct");
    }

    public Binding binding(Queue queue,DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).withQueueName();

    }
}
