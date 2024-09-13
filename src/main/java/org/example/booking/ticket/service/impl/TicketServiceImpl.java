package org.example.booking.ticket.service.impl;

import org.example.booking.ticket.entity.Ticket;
import org.example.booking.ticket.model.request.TicketCreateRequest;
import org.example.booking.ticket.model.response.TicketResponse;
import org.example.booking.ticket.repository.TicketRepository;
import org.example.booking.ticket.service.TicketService;
import org.example.booking.ticket.utils.MapperFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final MapperFactory mapperFactory;

    public TicketServiceImpl(TicketRepository ticketRepository, MapperFactory mapperFactory) {
        this.ticketRepository = ticketRepository;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public List<TicketResponse> getTickets() {
        return mapperFactory.getMapper(Ticket.class, TicketResponse.class).map(ticketRepository.findAll());
    }

    @Override
    public TicketResponse getTicketById(UUID id) {
        return mapperFactory.getMapper(Ticket.class, TicketResponse.class).map(ticketRepository.findById(id));
    }

    @Override
    public void save(TicketCreateRequest ticket) {
        ticketRepository.save(mapperFactory.getMapper(TicketCreateRequest.class, Ticket.class).map(ticket));
    }

    @Override
    public TicketResponse getTicketByLocation(long section, long seat, long row) {
        return mapperFactory.getMapper(Ticket.class, TicketResponse.class).map(ticketRepository.findByLocation(section, seat, row));
    }
}
