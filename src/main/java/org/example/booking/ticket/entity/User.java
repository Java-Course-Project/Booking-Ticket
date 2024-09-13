package org.example.booking.ticket.entity;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Builder.Default
    private UUID id = UUID.randomUUID();
    @Size(min = 2, max = 20)
    private String name;
    private LocalDate dob;
    // Use BigDecimal to avoid the floating problem with double
    @PositiveOrZero
    private BigDecimal balance;

    public User(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.dob = user.getDob();
        this.balance = user.getBalance();
    }
}
