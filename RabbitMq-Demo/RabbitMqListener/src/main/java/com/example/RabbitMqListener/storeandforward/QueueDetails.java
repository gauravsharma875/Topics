package com.example.RabbitMqListener.storeandforward;

import lombok.Value;

@Value
public class QueueDetails {

    private String queueName;
    private String exchangeName;

    public String getQueueName() {
        return queueName;
    }

    public String getExchangeName() {
        return exchangeName;
    }
}
