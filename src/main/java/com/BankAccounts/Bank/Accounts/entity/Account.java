package com.BankAccounts.Bank.Accounts.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    private String accountNo;

    private double balance;

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    List<User> userList = new ArrayList<>();

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    List<Transaction> transactionList = new ArrayList<>();
}
