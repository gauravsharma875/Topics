package com.example.RabbitMqListener.storeandforward;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static com.example.RabbitMqListener.storeandforward.beancontextUtils.
        ApplicationContextUtils.createBeanInApplicationContext;


@Slf4j
public class MessageBeansBuilder {

    private final ApplicationContext applicationContext;

    public MessageBeansBuilder(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public DelayedMessagingBeans createDelayedExchangeBeans(String exchangeName,
                                                            String queueName) {

        System.out.println(
                "Creating queues, binding and exchange -> exchange {}, queues {}"+
                exchangeName+ queueName);
        CustomExchange delayedExchange = delayedExchange(exchangeName);
        Queue queue = createQueue(queueName);
        Binding binding = delayedExchangeBinding(queue, delayedExchange);

        return new DelayedMessagingBeans(delayedExchange, queue, binding);
    }

    private Binding delayedExchangeBinding(Queue queue, CustomExchange customExchange) {
        return createBeanInApplicationContext(applicationContext, "binding-" +
                        queue.getName(),
                BindingBuilder.bind(queue).to(customExchange).with("routing-" +
                        queue.getName()).noargs());
    }

    private Queue createQueue(String queueName) {
        return createBeanInApplicationContext(applicationContext, queueName,
                new Queue(queueName, true));
    }

    private CustomExchange delayedExchange(String exchangeName) {

        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return createBeanInApplicationContext(applicationContext, exchangeName,
                new CustomExchange(exchangeName, "x-delayed-message",
                        true, false, args));
    }

    @Data
//    @AllArgsConstructor
    public static class DelayedMessagingBeans {
        private CustomExchange exchange;
        private Queue queue;
        private Binding binding;

        public DelayedMessagingBeans(CustomExchange delayedExchange,
                                     Queue queue, Binding binding) {
            this.exchange=delayedExchange;
            this.queue=queue;
            this.binding=binding;
        }
    }
}

