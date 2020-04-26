package com.example.RabbitMqListener.storeandforward;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;


@Slf4j
public class StoreAndForwardService {

    private RabbitTemplate rabbitTemplate;
    private String queueName;
    private String exchangeName;
    private int retryIntervalInMilliseconds;
    @SuppressWarnings("WeakerAccess")
    public static final String RETRY_COUNT_FOR_THIS_ATTEMPT =
            "X-Retry-Count-For-This-Attempt";

    public StoreAndForwardService(ApplicationContext applicationContext,
                                  RabbitTemplate rabbitTemplate, String queueName,
                                  String exchangeName, int retryIntervalInMilliseconds) {

        MessageBeansBuilder messageBeansBuilder = new MessageBeansBuilder(applicationContext);
        messageBeansBuilder.createDelayedExchangeBeans(exchangeName, queueName);

        this.rabbitTemplate = rabbitTemplate;
        this.queueName = queueName;
        this.exchangeName = exchangeName;
        this.retryIntervalInMilliseconds = retryIntervalInMilliseconds;
    }

    void storeAndForward(StoreAndForwardPayload storeAndForwardPayload,
                         QueueDetails queueDetails, MessageDetails messageDetails) {

        rabbitTemplate.convertAndSend(exchangeName, routingKey(), storeAndForwardPayload,
                message -> {
                    System.out.println("Sending a message with delay={} milliseconds" +
                            retryIntervalInMilliseconds);
                    message.getMessageProperties().setDelay(retryIntervalInMilliseconds);
                    message.getMessageProperties().setHeader(RETRY_COUNT_FOR_THIS_ATTEMPT,
                            storeAndForwardPayload.getCount());
                    return message;
                });
    }

    private String routingKey() {
        return "Routing-" + queueName;
    }
}
