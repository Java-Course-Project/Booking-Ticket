package org.example.booking.ticket.model.request;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateRequest {
    @Size(min = 2, max = 20)
    private String name;
    private LocalDate dob;
    // Use BigDecimal to avoid the floating problem with double
    @PositiveOrZero
    private BigDecimal balance;
}
