package com.example.baitap.controller;

import com.example.baitap.model.Customer;
import com.example.baitap.model.Deposit;
import com.example.baitap.model.Transfer;
import com.example.baitap.model.Withdraw;
import com.example.baitap.service.Transfer.TransferService;
import com.example.baitap.service.customer.CustomerService;
import com.example.baitap.service.deposit.DepositService;
import com.example.baitap.service.withdraw.WithdrawService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final TransferService transferService;
    private final DepositService depositService;
    private final WithdrawService withdrawService;

    @GetMapping("")
    public String showListPage(Model model) {
        List<Customer> customers = customerService.findAll(false);
        model.addAttribute("customers", customers);

        return "customer/list";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("customer", new Customer());

        return "customer/create";
    }

    @GetMapping("/edit/{customerId}")
    public String showEditPage(@PathVariable Long customerId, Model model) {
        Optional<Customer> customer = customerService.findById(customerId);
        model.addAttribute("customerUpdate", customer.get());

        return "customer/edit";
    }

    @GetMapping("/delete/{customerId}")
    public String deleteCustomer(@PathVariable Long customerId, RedirectAttributes redirectAttributes) {
        customerService.removeById(customerId);
        redirectAttributes.addFlashAttribute("success", true);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully");

        return "redirect:/customers";
    }

    @GetMapping("/deposit/{customerId}")
    public String showDepositPage(@PathVariable Long customerId, Model model) {
        try {
            Optional<Customer> customerOptional = customerService.findById(customerId);
            Customer customer = customerOptional.get();
            Deposit deposit = new Deposit();
            deposit.setCustomer(customer);
            model.addAttribute("deposit", deposit);

            return "banking/deposit";
        }catch (Exception ex){
            System.out.println(ex.getMessage() + customerId);
            return "banking/404";
        }
    }

    @GetMapping("/withdraw/{customerId}")
    public String showWithdrawPage(@PathVariable Long customerId, Model model) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        Customer customer = customerOptional.get();
        Withdraw withdraw = new Withdraw();
        withdraw.setCustomer(customer);
        model.addAttribute("withdraw", withdraw);

        return "banking/withdraw";
    }

    @GetMapping("/transfer/{senderId}")
    public String showTransferPage(@PathVariable Long senderId, Model model) {
        Optional<Customer> customerOptional = customerService.findById(senderId);
        Customer sender = customerOptional.get();
        List<Customer> recipients = customerService.findAllWithoutId(senderId);
        Transfer transfer = new Transfer();
        transfer.setSender(sender);
        model.addAttribute("transfer", transfer);
        model.addAttribute("recipients", recipients);

        return "banking/transfer";
    }

    @GetMapping("/history")
    public String showHistoryTransferPage(Model model) {
        List<Transfer> transfers = transferService.findAll(false);
        model.addAttribute("transfers", transfers);

        return "banking/history";
    }

    @GetMapping("/history-deposit")
    public String showHistoryDepositPage(Model model) {
        List<Deposit> deposits = depositService.findAll(false);
        model.addAttribute("deposits", deposits);

        return "banking/history-deposit";
    }

    @GetMapping("/history-withdraw")
    public String showHistoryWithdrawPage(Model model) {
        List<Withdraw> withdraws = withdrawService.findAll(false);
        model.addAttribute("withdraws", withdraws);

        return "banking/history-withdraw";
    }

    @PostMapping("/create")
    public String createCustomer(@Valid @ModelAttribute Customer customer, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
//        if (customer.getFullName().length() == 0) {
//            model.addAttribute("success", false);
//            model.addAttribute("message", "Created unsuccessful");
//
//            return "customer/create";
//        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Created unsuccessful");
            model.addAttribute("error", true);
            model.addAttribute("customer", customer);
            return "customer/create";
        }
        customerService.create(customer);
        redirectAttributes.addFlashAttribute("success", true);
        redirectAttributes.addFlashAttribute("message", "Created successfully");

        return "redirect:/customers";

    }

    @PostMapping("/edit/{customerId}")
    public String updateCustomer(@ModelAttribute Customer customer, @PathVariable Long customerId, Model model, RedirectAttributes redirectAttributes) {
        if (customer.getFullName().length() == 0) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Updated unsuccessful");
            model.addAttribute("customerUpdate", customer);

            return "customer/edit";
        } else {
            customerService.update(customerId, customer);
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", "Updated successfully");

            return "redirect:/customers";
        }
    }

    @PostMapping("/deposit/{customerId}")
    public String depositCustomer( @PathVariable Long customerId, @ModelAttribute Deposit deposit, Model model, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
    try {
        new Deposit().validate(deposit, bindingResult);
        Optional<Customer> customerOptional = customerService.findById(customerId);

        Customer customer = customerOptional.get();
        deposit.setCustomer(customer);

        if (bindingResult.hasErrors()) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Created unsuccessful");
            model.addAttribute("error", true);
            model.addAttribute("deposit", deposit);
            return "banking/deposit";
        }
        customerService.deposit(deposit);
        redirectAttributes.addFlashAttribute("success", true);
        redirectAttributes.addFlashAttribute("message", "Deposit successfully");
        return "redirect:/customers";
    }catch (Exception ex){
        System.out.println(ex.getMessage());
        return "banking/404";
    }

    }

    @PostMapping("/withdraw/{customerId}")
    public String withdrawCustomer(@PathVariable Long customerId, @ModelAttribute Withdraw withdraw, Model model, RedirectAttributes redirectAttributes) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        Customer customer = customerOptional.get();
        withdraw.setCustomer(customer);

        if (withdraw.getTransactionAmount().compareTo(BigDecimal.ZERO) == 0 || withdraw.getTransactionAmount() == null) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Withdraw amount must be greater than 0");
            model.addAttribute("withdraw", withdraw);

            return "banking/withdraw";
        } else if (withdraw.getTransactionAmount().compareTo(customer.getBalance()) > 0) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Insufficient balance to make a withdrawal");
            model.addAttribute("withdraw", withdraw);

            return "banking/withdraw";
        } else {
            customerService.withdraw(withdraw);
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", "Withdraw successful");

            return "redirect:/customers";
        }
    }

    @PostMapping("/transfer/{senderId}")
    public String transferCustomer(@Validated @PathVariable Long senderId, @ModelAttribute Transfer transfer,BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        new Transfer().validate(transfer,bindingResult);
        List<Customer> recipients = customerService.findAllWithoutId(senderId);
        Optional<Customer> customerOptional = customerService.findById(senderId);
        Customer customer = customerOptional.get();
        Optional<Customer> customerRecipient = customerService.findById(transfer.getRecipient().getId());
        Customer recipient = customerRecipient.get();
        transfer.setSender(customer);
        transfer.setRecipient(recipient);

        if (bindingResult.hasErrors()) {
            model.addAttribute("transfer", transfer);
            model.addAttribute("error", true);

            return "banking/transfer";
        } else if (transfer.getTransferAmount().compareTo(customer.getBalance()) > 0) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Insufficient balance to make a Transfer");
            model.addAttribute("transfer", transfer);
            model.addAttribute("recipients", recipients);

            return "banking/transfer";
        } else {
            customerService.transfer(transfer);
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", "Transfer successful");

            return "redirect:/customers";
        }
    }

}
