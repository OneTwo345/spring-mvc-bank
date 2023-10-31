package com.example.baitap.service.deposit;

import com.example.baitap.model.Customer;
import com.example.baitap.model.Deposit;
import com.example.baitap.model.Transfer;
import com.example.baitap.repository.IDepositRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class DepositService implements IDepositService{

    private IDepositRepository depositRepository;
    @Override
    public List<Deposit> findAll(boolean deleted) {
        return depositRepository.findAll();
    }

    @Override
    public Optional<Deposit> findById(Long id) {
        return depositRepository.findById(id);
    }

    @Override
    public void create(Deposit deposit) {
        deposit.setDateDeposit(LocalDateTime.now());
        depositRepository.save(deposit);
    }

    @Override
    public void update(Long id, Deposit deposit) {

    }

    @Override
    public void removeById(Long id) {

    }
}
