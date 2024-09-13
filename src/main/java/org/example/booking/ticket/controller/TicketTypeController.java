package org.example.booking.ticket.controller;

import org.example.booking.ticket.model.request.TicketTypeCreateRequest;
import org.example.booking.ticket.model.response.TicketTypeResponse;
import org.example.booking.ticket.service.TicketTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ticket-types")
public class TicketTypeController {
    private final TicketTypeService ticketTypeService;

    public TicketTypeController(TicketTypeService ticketTypeService) {
        this.ticketTypeService = ticketTypeService;
    }

    @GetMapping
    public ResponseEntity<List<TicketTypeResponse>> getTicketTypes() {
        return ResponseEntity.ok(ticketTypeService.getTicketTypes());
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody TicketTypeCreateRequest ticketTypeCreateRequest) {
        ticketTypeService.save(ticketTypeCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketTypeResponse> getTicketTypeById(@PathVariable UUID id) {
        return ResponseEntity.ok(ticketTypeService.getTicketTypeById(id));
    }
}
