package com.example.baitap.service.customer;

import com.example.baitap.model.Customer;
import com.example.baitap.model.Deposit;
import com.example.baitap.model.Transfer;
import com.example.baitap.model.Withdraw;
import com.example.baitap.service.IGeneralService;

import java.util.List;

public interface ICustomerService extends IGeneralService<Customer, Long> {
    void deposit(Deposit deposit);
    void withdraw(Withdraw withdraw);
    void transfer(Transfer transfer);
    List<Customer> findAllWithoutId(Long id);
}
