package org.example.booking.ticket.service;

import org.example.booking.ticket.model.request.TicketCreateRequest;
import org.example.booking.ticket.model.response.TicketResponse;

import java.util.List;
import java.util.UUID;

public interface TicketService {
    List<TicketResponse> getTickets();

    TicketResponse getTicketById(UUID id);

    void save(TicketCreateRequest ticket);

    TicketResponse getTicketByLocation(long section, long seat, long row);
}
