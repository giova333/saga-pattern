package com.gladunalexander.orderservice.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gladunalexander.orderservice.application.OrderCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderCreator orderCreator;

    @PostMapping
    public CompletableFuture<String> createOrder(@RequestBody CreateOrderRequest request) {
        return orderCreator.create(request.itemType, request.price, request.currency);
    }

    static class CreateOrderRequest {

        private final String itemType;
        private final BigDecimal price;
        private final String currency;

        @JsonCreator
        public CreateOrderRequest(@JsonProperty("itemType") String itemType,
                                  @JsonProperty("price") BigDecimal price,
                                  @JsonProperty("currency") String currency) {
            this.itemType = itemType;
            this.price = price;
            this.currency = currency;
        }
    }
}
