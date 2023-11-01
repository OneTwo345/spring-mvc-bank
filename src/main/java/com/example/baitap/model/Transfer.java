package com.example.baitap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transfers")
public class Transfer implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Customer sender;
    @ManyToOne
    private Customer recipient;


    private BigDecimal transferAmount;

    private Long fees;

    private BigDecimal feesAmount;

    @Column(name = "transation_amount", precision = 12, scale = 1, nullable = false)
    private BigDecimal transactionAmount;

    private LocalDateTime dateTransfer;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Transfer transfer = (Transfer) o;
        BigDecimal transferAmount = transfer.transferAmount;


        if (transferAmount == null) {
            errors.rejectValue("transferAmount", "transfer.transactionAmount.null");
            return;
        }
        if (transferAmount.compareTo(BigDecimal.TEN) < 0) {
            errors.rejectValue("transferAmount", "transfer.transactionAmount.min",
                    "Transaction amount must be greater than or equal to 10.");
        }
        if (transferAmount.compareTo(BigDecimal.valueOf(1500)) == 0) {
            errors.rejectValue("transferAmount", "transfer.transactionAmount.1500",
                    "Transaction amount must be different 1500.");
            return;
        }

        if (transferAmount.compareTo(BigDecimal.valueOf(1000)) > 0) {
            errors.rejectValue("transferAmount", "transfer.transactionAmount.max",
                    "Transaction amount must be smaller than or equal to 1000.");
        }
    }
}