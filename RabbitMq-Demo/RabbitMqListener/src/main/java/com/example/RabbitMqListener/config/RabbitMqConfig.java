package com.example.RabbitMqListener.config;

import com.example.RabbitMqListener.listerner.RabbitMqMessageListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String MY_QUEUE = "MyQueue";
    public static final String MY_TOPIC = "MyExchange";

    @Bean
    Queue myQueue() {
        return new Queue(MY_QUEUE, true);
    }

    @Bean
    Exchange myExchange() {
        return new TopicExchange(MY_TOPIC);
    }

    @Bean
    Binding binding() {
        /*return new Binding(MY_QUEUE, Binding.DestinationType.QUEUE, MY_QUEUE, "topic", null);*/
        return BindingBuilder.bind(myQueue()).to(myExchange()).with("topic").noargs();
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        return cachingConnectionFactory;
    }

    @Bean
    MessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer simpleMessageListerneContainer = new SimpleMessageListenerContainer();
        simpleMessageListerneContainer.setConnectionFactory(connectionFactory());
        simpleMessageListerneContainer.setQueues(myQueue());
        simpleMessageListerneContainer.setMessageListener(new RabbitMqMessageListener());
        return simpleMessageListerneContainer;
    }


}
