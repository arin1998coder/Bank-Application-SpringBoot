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
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String name;

    @ManyToOne
    @JoinColumn
    private Account account;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Transaction> transactionList = new ArrayList<>();

}
