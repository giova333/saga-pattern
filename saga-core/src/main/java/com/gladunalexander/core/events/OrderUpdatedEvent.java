package com.gladunalexander.core.events;

import lombok.Value;

@Value
public class OrderUpdatedEvent {

    String orderId;

    String orderStatus;
}
