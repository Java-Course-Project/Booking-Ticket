package org.example.booking.ticket.utils;

import org.example.booking.ticket.entity.TicketType;
import org.example.booking.ticket.model.response.TicketTypeResponse;
import org.springframework.stereotype.Component;

@Component
public class TicketTypeToTicketResponseMapper implements Mapper<TicketType, TicketTypeResponse> {
    @Override
    public TicketTypeResponse map(TicketType ticketType) {
        return TicketTypeResponse.builder()
                                 .id(ticketType.getId())
                                 .event(ticketType.getEvent())
                                 .type(ticketType.getType())
                                 .price(ticketType.getPrice())
                                 .build();
    }

    @Override
    public Class<TicketType> getSourceClass() {
        return TicketType.class;
    }

    @Override
    public Class<TicketTypeResponse> getDestinationClass() {
        return TicketTypeResponse.class;
    }
}
