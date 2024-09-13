package org.example.booking.ticket.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Ticket {
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private int section;
    private int seat;
    private int row;
    private LocalDateTime startTime;
    private UUID ticketTypeId;
    @NotNull
    private UUID ownerId;

    public Ticket(Ticket ticket) {
        this.id = ticket.getId();
        this.section = ticket.getSection();
        this.seat = ticket.getSeat();
        this.row = ticket.getRow();
        this.startTime = ticket.getStartTime();
        this.ticketTypeId = ticket.getTicketTypeId();
        this.ownerId = ticket.getOwnerId();
    }
}
