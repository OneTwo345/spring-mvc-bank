package com.example.baitap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "deposits")
public class Deposit implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Customer customer;

    private BigDecimal transactionAmount;
    private LocalDateTime dateDeposit;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Deposit deposit = (Deposit) o;
        BigDecimal transactionAmount = deposit.transactionAmount;

        if (transactionAmount.compareTo(BigDecimal.TEN) < 0) {
            errors.rejectValue("transactionAmount", "deposit.transactionAmount.min",
                    "Transaction amount must be greater than or equal to 10.");
        }
        if (transactionAmount.compareTo(BigDecimal.valueOf(1500)) == 0) {
            errors.rejectValue("transactionAmount", "deposit.transactionAmount.1500",
                    "Transaction amount must be different 1500.");
            return;
        }

        if (transactionAmount.compareTo(BigDecimal.valueOf(1000)) > 0) {
            errors.rejectValue("transactionAmount", "deposit.transactionAmount.max",
                    "Transaction amount must be smaller than or equal to 1000.");
        }


    }
}
