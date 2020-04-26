package com.example.RabbitMqListener.storeandforward;


import lombok.Data;

@Data
public abstract class StoreAndForwardPayload {
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    int count;
}
