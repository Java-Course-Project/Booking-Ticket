package org.example.booking.ticket.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketCreateRequest {
    private int section;
    private int seat;
    private int row;
    private LocalDateTime startTime;
    @JsonProperty("ticket_type_id")
    private UUID ticketTypeId;
}
