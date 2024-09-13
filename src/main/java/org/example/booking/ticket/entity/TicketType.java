package org.example.booking.ticket.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TicketType {
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String type;
    private String event;
    private BigDecimal price;

    public TicketType(TicketType ticketType) {
        this.id = ticketType.getId();
        this.type = ticketType.type;
        this.event = ticketType.event;
        this.price = ticketType.price;
    }
}
