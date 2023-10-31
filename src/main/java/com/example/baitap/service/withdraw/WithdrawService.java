package com.example.baitap.service.withdraw;

import com.example.baitap.model.Deposit;
import com.example.baitap.model.Withdraw;
import com.example.baitap.repository.IWithdrawRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class WithdrawService implements IWithdrawService{

    private IWithdrawRepository withdrawRepository;
    @Override
    public List<Withdraw> findAll(boolean deleted) {
        return withdrawRepository.findAll();
    }

    @Override
    public Optional<Withdraw> findById(Long id) {
        return withdrawRepository.findById(id);
    }

    @Override
    public void create(Withdraw withdraw) {
        withdraw.setDateWithdrawal(LocalDateTime.now());
        withdrawRepository.save(withdraw);
    }

    @Override
    public void update(Long id, Withdraw withdraw) {

    }

    @Override
    public void removeById(Long id) {

    }
}
