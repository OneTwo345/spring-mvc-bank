package com.example.baitap.repository;

import com.example.baitap.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransferRepository extends JpaRepository<Transfer, Long> {
}
