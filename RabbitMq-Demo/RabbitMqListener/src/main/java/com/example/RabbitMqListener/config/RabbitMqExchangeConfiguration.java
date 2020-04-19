package com.example.RabbitMqListener.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*There are different ways to create Exchanges
 * @gaurav
 * */

@Configuration
public class RabbitMqExchangeConfiguration {

    @Bean
    Exchange exchange() {
        return new TopicExchange("TopicExchange1");
    }

    /*
    Internal -
    Note that the setting called "Internal" is for exchanges that do
    not have an external client connecting to them. In the case of the
    three main exchanges we're configuring, the message producer will
     need to connect to them so we'll leave the internal setting at "No".


    If set, the exchange may not be used directly by publishers, but
    only when bound to other exchanges. Internal exchanges are used to
    construct wiring that is not visible to applications.*/

    @Bean
    Exchange directExchange() {
        return ExchangeBuilder.directExchange("DirectExchange").autoDelete()
                .internal().build();
    }

    @Bean
    Exchange topicExchange() {
        return ExchangeBuilder.topicExchange("TopicExchange").autoDelete()
                .durable(true).internal().build();
    }

    @Bean
    Exchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange("FanoutExchange").autoDelete()
                .durable(false).internal().build();
    }

    @Bean
    Exchange headersExchange() {
        return ExchangeBuilder.headersExchange("HeadersExchange2").autoDelete()
                .durable(true).ignoreDeclarationExceptions().build();
    }

}
