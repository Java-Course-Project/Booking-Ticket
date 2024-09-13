package org.example.booking.ticket.repository;

import jakarta.validation.Valid;
import org.example.booking.ticket.entity.Ticket;
import org.example.booking.ticket.entity.TicketType;
import org.example.booking.ticket.exception.DataInvalidException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

@Repository
public class TicketTypeRepository implements CrudRepository<TicketType, UUID> {
    private static final Set<TicketType> ticketTypes = new CopyOnWriteArraySet<>();

    @Override
    public TicketType findById(UUID id) {
        return ticketTypes.stream().filter(e -> e.getId().equals(id)).map(TicketType::new).findFirst().orElse(null);
    }

    @Override
    public List<TicketType> findAll() {
        return new ArrayList<>(ticketTypes.stream().map(TicketType::new).toList());
    }

    @Override
    public void save(@Valid TicketType ticketType) {
        if (ticketType.getId() == null) {
            throw new DataInvalidException("Id must not be null");
        }

        DatabaseLocking.acquireTableLock(TicketType.class);
        try {
            for (TicketType type : ticketTypes) {
                if (type.getId().equals(ticketType.getId())) {
                    throw new DataInvalidException("Ticket type id already exists");
                }
            }
            ticketTypes.add(ticketType);
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
            TicketType existed = ticketTypes.stream().filter(e -> e.getId().equals(ticketType.getId())).findFirst().orElse(null);
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
