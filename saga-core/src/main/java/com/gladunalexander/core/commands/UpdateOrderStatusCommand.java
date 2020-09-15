package com.gladunalexander.core.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class UpdateOrderStatusCommand {

    @TargetAggregateIdentifier
    String orderId;

    String orderStatus;
}
