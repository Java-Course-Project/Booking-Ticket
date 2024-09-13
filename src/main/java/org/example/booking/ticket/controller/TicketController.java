package org.example.booking.ticket.controller;

import org.example.booking.ticket.model.request.TicketCreateRequest;
import org.example.booking.ticket.model.response.TicketResponse;
import org.example.booking.ticket.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getTickets() {
        return ResponseEntity.ok(ticketService.getTickets());
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody TicketCreateRequest ticketCreateRequest) {
        ticketService.save(ticketCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable UUID id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @GetMapping("/location")
    public ResponseEntity<TicketResponse> getTicketByLocation(@RequestParam long section, @RequestParam long seat, @RequestParam long row) {
        return ResponseEntity.ok(ticketService.getTicketByLocation(section, seat, row));
    }
}
