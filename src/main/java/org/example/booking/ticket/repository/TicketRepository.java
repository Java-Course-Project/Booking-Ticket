package org.example.booking.ticket.repository;

import jakarta.validation.Valid;
import org.example.booking.ticket.entity.Ticket;
import org.example.booking.ticket.entity.TicketType;
import org.example.booking.ticket.exception.DataInvalidException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TicketRepository implements CrudRepository<Ticket, UUID> {
    private static final Map<UUID, Ticket> tickets = new ConcurrentHashMap<>();
    private final TicketTypeRepository ticketTypeRepository;

    public TicketRepository(TicketTypeRepository ticketTypeRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
    }

    @Override
    public Ticket findById(UUID uuid) {
        return new Ticket(tickets.get(uuid));
    }

    @Override
    public List<Ticket> findAll() {
        return new ArrayList<>(tickets.values().stream().map(Ticket::new).toList());
    }

    @Override
    public void save(@Valid Ticket ticket) {
        if (ticket.getId() == null) {
            throw new DataInvalidException("Id must not be null");
        }

        // TODO: implement a row lock for ticket type for better performance
        DatabaseLocking.acquireTableLock(Ticket.class);
        DatabaseLocking.acquireTableLock(TicketType.class);
        try {
            Collection<Ticket> existingTickets = tickets.values();
            if (tickets.containsKey(ticket.getId())) {
                throw new DataInvalidException("Ticket id already exists");
            }

            for (Ticket t : existingTickets) {
                if (t.getSection() == ticket.getSection() && t.getSeat() == ticket.getSeat() && t.getRow() == ticket.getRow()) {
                    throw new DataInvalidException("Ticket seat not available");
                }
            }

            if (ticketTypeRepository.findById(ticket.getTicketTypeId()) == null) {
                throw new DataInvalidException("Ticket type id not exists");
            }

            tickets.put(ticket.getId(), ticket);
        } finally {
            DatabaseLocking.releaseTableLock(Ticket.class);
            DatabaseLocking.releaseTableLock(TicketType.class);
        }
    }

    @Override
    public void update(@Valid Ticket ticket) {
        if (ticket.getId() == null) {
            throw new DataInvalidException("Id must not be null");
        }

        DatabaseLocking.acquireTableLock(Ticket.class);
        DatabaseLocking.acquireTableLock(TicketType.class);
        try {
            Ticket existed = tickets.get(ticket.getId());
            if (existed == null) {
                throw new DataInvalidException("Ticket id not exists");
            }

            existed.setSection(ticket.getSection());
            existed.setSeat(ticket.getSeat());
            existed.setRow(ticket.getRow());
            existed.setTicketTypeId(ticket.getTicketTypeId());
            existed.setStartTime(ticket.getStartTime());
            existed.setOwnerId(ticket.getOwnerId());
        } finally {
            DatabaseLocking.releaseTableLock(Ticket.class);
            DatabaseLocking.releaseTableLock(TicketType.class);
        }
    }

    public Ticket findByLocation(long section, long seat, long row) {
        return tickets.values().stream().filter(e -> e.getSection() == section && e.getSeat() == seat && e.getRow() == row).map(Ticket::new)
                      .findFirst().orElse(null);
    }
}
