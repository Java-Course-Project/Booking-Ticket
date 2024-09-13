package org.example.booking.ticket.service;

import org.example.booking.ticket.model.request.UserCreateRequest;
import org.example.booking.ticket.model.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponse> getUsers();

    void save(UserCreateRequest user);

    UserResponse getUserById(UUID id);

    void buyTicket(UUID ticketId, UUID userId);
}
