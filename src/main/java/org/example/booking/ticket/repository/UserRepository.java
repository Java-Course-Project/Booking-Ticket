package org.example.booking.ticket.repository;

import jakarta.validation.Valid;
import org.example.booking.ticket.entity.User;
import org.example.booking.ticket.exception.DataInvalidException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

@Repository
public class UserRepository implements CrudRepository<User, UUID> {
    private static final Set<User> users = new CopyOnWriteArraySet<>();

    @Override
    public User findById(UUID uuid) {
        return users.stream().filter(e -> e.getId().equals(uuid)).map(User::new).findFirst().orElse(null);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.stream().map(User::new).toList());
    }

    @Override
    public void save(@Valid User user) {
        if (user.getId() == null) {
            throw new DataInvalidException("Id must not be null");
        }

        DatabaseLocking.acquireTableLock(User.class);
        try {
            for (User u : users) {
                if (u.getId().equals(user.getId())) {
                    throw new DataInvalidException("User id already exists");
                }
                if (u.getName().equals(user.getName())) {
                    throw new DataInvalidException("User name already exists");
                }
            }
            users.add(user);
        } finally {
            DatabaseLocking.releaseTableLock(User.class);
        }

    }

    @Override
    public void update(User user) {
        DatabaseLocking.acquireTableLock(User.class);
        try {
            User existed = users.stream().filter(e -> e.getId().equals(user.getId())).findFirst().orElse(null);
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
