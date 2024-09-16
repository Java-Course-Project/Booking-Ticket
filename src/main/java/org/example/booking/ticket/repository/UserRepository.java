package org.example.booking.ticket.repository;

import jakarta.validation.Valid;
import org.example.booking.ticket.entity.User;
import org.example.booking.ticket.exception.DataInvalidException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository implements CrudRepository<User, UUID> {
    private static final Map<UUID, User> users = new ConcurrentHashMap<>();

    @Override
    public User findById(UUID uuid) {
        return new User(users.get(uuid));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values().stream().map(User::new).toList());
    }

    @Override
    public void save(@Valid User user) {
        if (user.getId() == null) {
            throw new DataInvalidException("Id must not be null");
        }

        DatabaseLocking.acquireTableLock(User.class);
        try {
            Collection<User> existedUsers = users.values();
            if (users.containsKey(user.getId())) {
                throw new DataInvalidException("User id already exists");
            }

            for (User u : existedUsers) {
                if (u.getName().equals(user.getName())) {
                    throw new DataInvalidException("User name already exists");
                }
            }
            users.put(user.getId(), user);
        } finally {
            DatabaseLocking.releaseTableLock(User.class);
        }

    }

    @Override
    public void update(User user) {
        DatabaseLocking.acquireTableLock(User.class);
        try {
            User existed = users.get(user.getId());
            if (existed == null) {
                throw new DataInvalidException("User not found");
            }

            existed.setName(user.getName());
            existed.setBalance(user.getBalance());
            existed.setBalance(user.getBalance());
            existed.setDob(user.getDob());
        } finally {
            DatabaseLocking.releaseTableLock(User.class);
        }
    }
}
