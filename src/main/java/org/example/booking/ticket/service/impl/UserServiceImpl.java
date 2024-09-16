package org.example.booking.ticket.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.booking.ticket.entity.Ticket;
import org.example.booking.ticket.entity.TicketType;
import org.example.booking.ticket.entity.User;
import org.example.booking.ticket.exception.DataInvalidException;
import org.example.booking.ticket.model.request.UserCreateRequest;
import org.example.booking.ticket.model.response.UserResponse;
import org.example.booking.ticket.repository.DatabaseLocking;
import org.example.booking.ticket.repository.TicketRepository;
import org.example.booking.ticket.repository.TicketTypeRepository;
import org.example.booking.ticket.repository.UserRepository;
import org.example.booking.ticket.service.UserService;
import org.example.booking.ticket.utils.MapperFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final TicketRepository ticketRepository;
	private final TicketTypeRepository ticketTypeRepository;
	private final MapperFactory factory;

	public UserServiceImpl(UserRepository userRepository, TicketRepository ticketRepository, TicketTypeRepository ticketTypeRepository,
						   MapperFactory factory) {
		this.userRepository = userRepository;
		this.ticketRepository = ticketRepository;
		this.ticketTypeRepository = ticketTypeRepository;
		this.factory = factory;
	}

	@Override
	public List<UserResponse> getUsers() {
		return factory.getMapper(User.class, UserResponse.class).map(userRepository.findAll());
	}

	@Override
	public void save(UserCreateRequest userCreateRequest) {
		User user = factory.getMapper(UserCreateRequest.class, User.class).map(userCreateRequest);
		userRepository.save(user);
	}

	@Override
	public UserResponse getUserById(UUID id) {
		return factory.getMapper(User.class, UserResponse.class).map(userRepository.findById(id));
	}

	@Override
	public void buyTicket(UUID ticketId, UUID userId) {
		DatabaseLocking.acquireTableLock(User.class);
		DatabaseLocking.acquireTableLock(Ticket.class);
		DatabaseLocking.acquireTableLock(TicketType.class);

		try {
			User user = userRepository.findById(userId);
			if (user == null) {
				throw new DataInvalidException("User not found");
			}

			Ticket ticket = ticketRepository.findById(ticketId);
			if (ticket == null) {
				throw new DataInvalidException("Ticket not found");
			}

			if (ticket.getOwnerId() != null) {
				throw new DataInvalidException("Ticket is already bought");
			}

			TicketType ticketType = ticketTypeRepository.findById(ticket.getTicketTypeId());
			if (ticketType == null) {
				throw new DataInvalidException("Ticket type not found");
			}

			if (ticketType.getPrice().compareTo(user.getBalance()) > 0) {
				throw new DataInvalidException("Not enough balance");
			}

			user.setBalance(user.getBalance().subtract(ticketType.getPrice()));
			ticket.setOwnerId(user.getId());

			userRepository.update(user);
			ticketRepository.update(ticket);
		} finally {
			DatabaseLocking.releaseTableLock(User.class);
			DatabaseLocking.releaseTableLock(Ticket.class);
			DatabaseLocking.releaseTableLock(TicketType.class);
		}
	}
}
