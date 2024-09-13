package org.example.booking.ticket.controller;

import org.example.booking.ticket.model.request.UserCreateRequest;
import org.example.booking.ticket.model.response.UserResponse;
import org.example.booking.ticket.service.UserService;
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
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserCreateRequest user) {
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/{id}/ticket/{ticket-id}")
    public ResponseEntity<Void> buyTicket(@PathVariable UUID id, @PathVariable(name = "ticket-id") UUID ticketId) {
        userService.buyTicket(ticketId, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
