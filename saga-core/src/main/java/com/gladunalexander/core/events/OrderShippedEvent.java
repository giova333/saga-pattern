package com.gladunalexander.core.events;

import lombok.Value;

@Value
public class OrderShippedEvent {

    String shippingId;

    String orderId;

    String paymentId;
}
