package com.gladunalexander.paymentservice.domain;

import com.gladunalexander.core.commands.CreateInvoiceCommand;
import com.gladunalexander.core.events.InvoiceCreatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Slf4j
@Aggregate
@NoArgsConstructor
public class InvoiceAggregate {

    @AggregateIdentifier
    private String paymentId;

    private String orderId;

    private InvoiceStatus invoiceStatus;


    @CommandHandler
    public InvoiceAggregate(CreateInvoiceCommand createInvoiceCommand) {
        AggregateLifecycle.apply(
                new InvoiceCreatedEvent(
                        createInvoiceCommand.getPaymentId(),
                        createInvoiceCommand.getOrderId()
                )
        );
    }

    @EventSourcingHandler
    protected void on(InvoiceCreatedEvent invoiceCreatedEvent) {
        log.info("Invoice Created#### {}", invoiceCreatedEvent);
        this.paymentId = invoiceCreatedEvent.getPaymentId();
        this.orderId = invoiceCreatedEvent.getOrderId();
        this.invoiceStatus = InvoiceStatus.PAID;
    }
}
