package com.example.baitap.repository;

import com.example.baitap.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {


    List<Customer> findCustomerByDeletedIsFalse();
}
