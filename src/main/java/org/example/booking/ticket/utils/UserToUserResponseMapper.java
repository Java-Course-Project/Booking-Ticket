package org.example.booking.ticket.utils;

import org.example.booking.ticket.entity.User;
import org.example.booking.ticket.model.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserToUserResponseMapper implements Mapper<User, UserResponse> {
    @Override
    public UserResponse map(User user) {
        return UserResponse.builder()
                           .id(user.getId())
                           .name(user.getName())
                           .dob(user.getDob())
                           .balance(user.getBalance())
                           .build();
    }

    @Override
    public Class<User> getSourceClass() {
        return User.class;
    }

    @Override
    public Class<UserResponse> getDestinationClass() {
        return UserResponse.class;
    }
}
