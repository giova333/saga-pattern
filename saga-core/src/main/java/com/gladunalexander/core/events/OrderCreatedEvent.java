package com.gladunalexander.core.events;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class OrderCreatedEvent {

    String orderId;

    String itemType;

    BigDecimal price;

    String currency;

    String orderStatus;
}
