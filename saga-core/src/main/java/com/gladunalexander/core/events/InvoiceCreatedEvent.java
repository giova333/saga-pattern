package com.gladunalexander.core.events;

import lombok.Value;

@Value
public class InvoiceCreatedEvent {

    String paymentId;

    String orderId;
}
