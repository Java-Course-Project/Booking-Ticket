package org.example.booking.ticket.utils;

import org.example.booking.ticket.entity.User;
import org.example.booking.ticket.model.request.UserCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class UserCreateRequestToUserMapper implements Mapper<UserCreateRequest, User> {

    @Override
    public User map(UserCreateRequest userCreateRequest) {
        return User.builder()
                   .dob(userCreateRequest.getDob())
                   .balance(userCreateRequest.getBalance())
                   .name(userCreateRequest.getName())
                   .build();
    }

    @Override
    public Class<UserCreateRequest> getSourceClass() {
        return UserCreateRequest.class;
    }

    @Override
    public Class<User> getDestinationClass() {
        return User.class;
    }
}
