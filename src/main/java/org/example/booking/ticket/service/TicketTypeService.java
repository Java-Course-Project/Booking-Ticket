package org.example.booking.ticket.service;

import org.example.booking.ticket.model.request.TicketTypeCreateRequest;
import org.example.booking.ticket.model.response.TicketTypeResponse;

import java.util.List;
import java.util.UUID;

public interface TicketTypeService {
    List<TicketTypeResponse> getTicketTypes();

    void save(TicketTypeCreateRequest ticketType);

    TicketTypeResponse getTicketTypeById(UUID id);
}
