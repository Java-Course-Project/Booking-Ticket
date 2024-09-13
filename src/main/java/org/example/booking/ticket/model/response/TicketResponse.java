package org.example.booking.ticket.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class TicketResponse {
    private UUID id;
    private int section;
    private int seat;
    private int row;
    @JsonProperty("start_time")
    private LocalDateTime startTime;
    @JsonProperty("ticket_type_id")
    private UUID ticketTypeId;
    @JsonProperty("owner_id")
    private UUID ownerId;
}
