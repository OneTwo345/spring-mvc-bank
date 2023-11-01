package com.example.baitap.service.customer;

import com.example.baitap.exception.ResourceNotFoundException;
import com.example.baitap.model.Customer;
import com.example.baitap.model.Deposit;
import com.example.baitap.model.Transfer;
import com.example.baitap.model.Withdraw;
import com.example.baitap.repository.ICustomerRepository;
import com.example.baitap.service.Transfer.TransferService;
import com.example.baitap.service.deposit.DepositService;
import com.example.baitap.service.withdraw.WithdrawService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class CustomerService implements ICustomerService{

    private ICustomerRepository customerRepository;

    private DepositService depositService;

    private WithdrawService withdrawService;

    private TransferService transferService;
    @Override
    public List<Customer> findAll(boolean deleted) {
        return customerRepository.findCustomerByDeletedIsFalse();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sorry, no customer found with the Id :" + id)));
    }

    @Override
    public void create(Customer customer) {
        customer.setBalance(BigDecimal.valueOf(0));
        customerRepository.save(customer);
    }

    @Override
    public void update(Long id, Customer customer) {
        Optional<Customer> existingCustomer = findById(id);
        Customer customerUpdate = existingCustomer.get();
        customerUpdate.setFullName(customer.getFullName());
        customerUpdate.setEmail(customer.getEmail());
        customerUpdate.setPhone(customer.getPhone());
        customerUpdate.setAddress(customer.getAddress());
        customerRepository.save(customerUpdate);
    }

    @Override
    public void removeById(Long id) {
        Optional<Customer> existingCustomer = findById(id);
        Customer customer = existingCustomer.get();
        customer.setDeleted(true);
        customerRepository.save(customer);
    }

    @Override
    public void deposit(Deposit deposit) {
        Customer customer = deposit.getCustomer();
        BigDecimal currentBalance = customer.getBalance();
        BigDecimal transactionAmount = deposit.getTransactionAmount();
        BigDecimal newBalance = currentBalance.add(transactionAmount);
        customer.setBalance(newBalance);

        customerRepository.save(customer);
        depositService.create(deposit);
    }

    @Override
    public void withdraw(Withdraw withdraw) {
        Customer customer = withdraw.getCustomer();
        BigDecimal currentBalance = customer.getBalance();
        BigDecimal transactionAmount = withdraw.getTransactionAmount();
        BigDecimal newBalance = currentBalance.subtract(transactionAmount);
        customer.setBalance(newBalance);

        customerRepository.save(customer);
        withdrawService.create(withdraw);
    }

    @Override
    public void transfer(Transfer transfer) {
        Customer sender = transfer.getSender();
        Customer recipient = transfer.getRecipient();
        BigDecimal transferAmount = transfer.getTransferAmount();
        Long fees = 10L;
        BigDecimal feesAmount = transferAmount.multiply(BigDecimal.valueOf(fees)).divide(BigDecimal.valueOf(100));
        BigDecimal transactionAmount = transferAmount.add(feesAmount);
        BigDecimal senderBalance = sender.getBalance();
        BigDecimal newSenderBalance = senderBalance.subtract(transactionAmount);
        sender.setBalance(newSenderBalance);
        BigDecimal recipientBalance = recipient.getBalance();
        BigDecimal newRecipientBalance = recipientBalance.add(transferAmount);
        recipient.setBalance(newRecipientBalance);

        customerRepository.save(sender);
        customerRepository.save(recipient);

        transfer.setFees(fees);
        transfer.setFeesAmount(feesAmount);
        transfer.setTransactionAmount(transactionAmount);
        transferService.create(transfer);
    }

    @Override
    public List<Customer> findAllWithoutId(Long id) {
        List<Customer> allCustomers = customerRepository.findCustomerByDeletedIsFalse();
        List<Customer> customers = allCustomers.stream()
                .filter(customer -> !customer.getId().equals(id))
                .collect(Collectors.toList());

        return customers;
    }

}
