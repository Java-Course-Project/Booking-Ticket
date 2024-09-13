package org.example.booking.ticket.utils;

import org.example.booking.ticket.entity.Ticket;
import org.example.booking.ticket.model.response.TicketResponse;
import org.springframework.stereotype.Component;

@Component
public class TicketToTicketResponseMapper implements Mapper<Ticket, TicketResponse> {
    @Override
    public TicketResponse map(Ticket ticket) {
        return TicketResponse.builder()
                             .id(ticket.getId())
                             .row(ticket.getRow())
                             .seat(ticket.getSeat())
                             .ownerId(ticket.getOwnerId())
                             .section(ticket.getSection())
                             .startTime(ticket.getStartTime())
                             .ticketTypeId(ticket.getTicketTypeId())
                             .build();
    }

    @Override
    public Class<Ticket> getSourceClass() {
        return Ticket.class;
    }

    @Override
    public Class<TicketResponse> getDestinationClass() {
        return TicketResponse.class;
    }
}
