package com.example.baitap.repository;

import com.example.baitap.model.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWithdrawRepository extends JpaRepository<Withdraw, Long> {
}
