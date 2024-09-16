package org.example.booking.ticket.repository;

import jakarta.validation.Valid;
import org.example.booking.ticket.entity.Ticket;
import org.example.booking.ticket.entity.TicketType;
import org.example.booking.ticket.exception.DataInvalidException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TicketTypeRepository implements CrudRepository<TicketType, UUID> {
    private static final Map<UUID, TicketType> ticketTypes = new ConcurrentHashMap<>();

    @Override
    public TicketType findById(UUID id) {
        return new TicketType(ticketTypes.get(id));
    }

    @Override
    public List<TicketType> findAll() {
        return new ArrayList<>(ticketTypes.values().stream().map(TicketType::new).toList());
    }

    @Override
    public void save(@Valid TicketType ticketType) {
        if (ticketType.getId() == null) {
            throw new DataInvalidException("Id must not be null");
        }

        DatabaseLocking.acquireTableLock(TicketType.class);
        try {
            if (ticketTypes.containsKey(ticketType.getId())) {
                throw new DataInvalidException("TicketType with id " + ticketType.getId() + " already exists");
            }
            ticketTypes.put(ticketType.getId(), ticketType);
        } finally {
            DatabaseLocking.releaseTableLock(TicketType.class);
        }

    }

    @Override
    public void update(TicketType ticketType) {
        if (ticketType.getId() == null) {
            throw new DataInvalidException("Id must not be null");
        }

        DatabaseLocking.acquireTableLock(TicketType.class);
        try {
            TicketType existed = ticketTypes.get(ticketType.getId());
            if (existed == null) {
                throw new DataInvalidException("Ticket id not exists");
            }

            existed.setType(ticketType.getType());
            existed.setEvent(ticketType.getEvent());
            existed.setPrice(ticketType.getPrice());
        } finally {
            DatabaseLocking.releaseTableLock(Ticket.class);
            DatabaseLocking.releaseTableLock(TicketType.class);
        }
    }
}
