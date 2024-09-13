package org.example.booking.ticket.utils;

import org.example.booking.ticket.entity.TicketType;
import org.example.booking.ticket.model.request.TicketTypeCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class TicketTypeCreateRequestToTicketTypeMapper implements Mapper<TicketTypeCreateRequest, TicketType> {
    @Override
    public TicketType map(TicketTypeCreateRequest ticketTypeCreateRequest) {
        return TicketType.builder()
                         .event(ticketTypeCreateRequest.getEvent())
                         .type(ticketTypeCreateRequest.getType())
                         .price(ticketTypeCreateRequest.getPrice())
                         .build();
    }

    @Override
    public Class<TicketTypeCreateRequest> getSourceClass() {
        return TicketTypeCreateRequest.class;
    }

    @Override
    public Class<TicketType> getDestinationClass() {
        return TicketType.class;
    }
}
