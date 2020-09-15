package com.gladunalexander.core.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Value
public class CreateOrderCommand {

    @TargetAggregateIdentifier
    String orderId;

    String itemType;

    BigDecimal price;

    String currency;

    String orderStatus;
}
