package com.example.RabbitMqListener.config;

/*There are different ways to create queues
 * @gaurav
 * */

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqQueueConfiguration {

    /*Example 1*/
    @Bean
    Queue queue1() {
        return new Queue("Queue1", false);
    }


    /*Example 2
    exclusive - When set to true, your queue becomes private and can only be consumed by your app.
    This is useful when you need to limit a queue to only one consumer.

    auto-delete - The queue is automatically deleted when the last consumer unsubscribes.*/
    @Bean
    Queue queue2() {
        return QueueBuilder.durable("Queue2").autoDelete().exclusive().build();
    }

}
