package org.example.booking.ticket.utils;

import org.example.booking.ticket.entity.Ticket;
import org.example.booking.ticket.model.request.TicketCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class TicketCreateRequestToTicketMapper implements Mapper<TicketCreateRequest, Ticket> {
    @Override
    public Ticket map(TicketCreateRequest ticketCreateRequest) {
        return Ticket.builder()
                     .row(ticketCreateRequest.getRow())
                     .seat(ticketCreateRequest.getSeat())
                     .ticketTypeId(ticketCreateRequest.getTicketTypeId())
                     .section(ticketCreateRequest.getSection())
                     .startTime(ticketCreateRequest.getStartTime())
                     .build();
    }

    @Override
    public Class<TicketCreateRequest> getSourceClass() {
        return TicketCreateRequest.class;
    }

    @Override
    public Class<Ticket> getDestinationClass() {
        return Ticket.class;
    }
}
