package com.example.baitap.repository;

import com.example.baitap.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepositRepository extends JpaRepository<Deposit, Long> {
}
