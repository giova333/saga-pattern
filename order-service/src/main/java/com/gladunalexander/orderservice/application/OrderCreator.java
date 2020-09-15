package com.gladunalexander.orderservice.application;

import com.gladunalexander.orderservice.domain.OrderStatus;
import com.gladunalexander.core.commands.CreateOrderCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class OrderCreator {

    private final CommandGateway commandGateway;

    public CompletableFuture<String> create(String itemType, BigDecimal price, String currency) {
        return commandGateway.send(
                new CreateOrderCommand(
                        UUID.randomUUID().toString(),
                        itemType,
                        price,
                        currency,
                        OrderStatus.CREATED.toString()
                )
        );
    }

}
