package com.example.baitap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "ten khong duoc de trong")
    private String fullName;
    @NotBlank(message = "email khong duoc de trong")
    @Email(message = "email phai dung dinh dang")
    private String email;
    @NotBlank(message = "phone khong duoc de trong")
    private String phone;
    private String address;
    private BigDecimal balance;
    private boolean deleted;


}
