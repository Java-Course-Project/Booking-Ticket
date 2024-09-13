package org.example.booking.ticket.service.impl;

import org.example.booking.ticket.entity.TicketType;
import org.example.booking.ticket.model.request.TicketTypeCreateRequest;
import org.example.booking.ticket.model.response.TicketTypeResponse;
import org.example.booking.ticket.repository.TicketTypeRepository;
import org.example.booking.ticket.service.TicketTypeService;
import org.example.booking.ticket.utils.MapperFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TicketTypeServiceImpl implements TicketTypeService {
    private final TicketTypeRepository ticketTypeRepository;
    private final MapperFactory mapperFactory;

    public TicketTypeServiceImpl(TicketTypeRepository ticketTypeRepository, MapperFactory mapperFactory) {
        this.ticketTypeRepository = ticketTypeRepository;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public List<TicketTypeResponse> getTicketTypes() {
        return mapperFactory.getMapper(TicketType.class, TicketTypeResponse.class).map(ticketTypeRepository.findAll());
    }

    @Override
    public void save(TicketTypeCreateRequest ticketType) {
        ticketTypeRepository.save(mapperFactory.getMapper(TicketTypeCreateRequest.class, TicketType.class).map(ticketType));
    }

    @Override
    public TicketTypeResponse getTicketTypeById(UUID id) {
        return mapperFactory.getMapper(TicketType.class, TicketTypeResponse.class).map(ticketTypeRepository.findById(id));
    }
}
