package com.gladunalexander.core.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CreateShippingCommand {

    @TargetAggregateIdentifier
    String shippingId;

    String orderId;

    String paymentId;
}
