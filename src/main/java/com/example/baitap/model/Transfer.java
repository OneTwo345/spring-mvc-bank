package com.example.baitap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Transfer {
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
    private BigDecimal transactionAmount;
    private LocalDateTime dateTransfer;
}
