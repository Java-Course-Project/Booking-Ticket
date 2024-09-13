package org.example.booking.ticket.repository;

import jakarta.validation.Valid;
import org.example.booking.ticket.entity.Ticket;
import org.example.booking.ticket.entity.TicketType;
import org.example.booking.ticket.exception.DataInvalidException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

@Repository
public class TicketRepository implements CrudRepository<Ticket, UUID> {
    private static final Set<Ticket> tickets = new CopyOnWriteArraySet<>();
    private final TicketTypeRepository ticketTypeRepository;

    public TicketRepository(TicketTypeRepository ticketTypeRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
    }

    @Override
    public Ticket findById(UUID uuid) {
        return tickets.stream().filter(e -> e.getId().equals(uuid)).map(Ticket::new).findFirst().orElse(null);
    }

    @Override
    public List<Ticket> findAll() {
        return tickets.stream().map(Ticket::new).toList();
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
            for (Ticket t : tickets) {
                if (t.getId().equals(ticket.getId())) {
                    throw new DataInvalidException("Ticket id already exists");
                }

                if (t.getSection() == ticket.getSection() && t.getSeat() == ticket.getSeat() && t.getRow() == ticket.getRow()) {
                    throw new DataInvalidException("Ticket seat not available");
                }
            }

            if (ticketTypeRepository.findById(ticket.getTicketTypeId()) == null) {
                throw new DataInvalidException("Ticket type id not exists");
            }
            tickets.add(ticket);
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
            Ticket existed = tickets.stream().filter(e -> e.getId().equals(ticket.getId())).findFirst().orElse(null);
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
        return tickets.stream().filter(e -> e.getSection() == section && e.getSeat() == seat && e.getRow() == row).map(Ticket::new)
                      .findFirst().orElse(null);
    }
}
