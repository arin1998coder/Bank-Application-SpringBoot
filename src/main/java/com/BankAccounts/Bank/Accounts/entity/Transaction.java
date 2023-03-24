package com.BankAccounts.Bank.Accounts.entity;

import com.BankAccounts.Bank.Accounts.Enums.TransactionStatus;
import com.BankAccounts.Bank.Accounts.Enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @CreationTimestamp
    private Date date;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    private double amount;

    @ManyToOne
    @JoinColumn
    private Account account;

    @ManyToOne
    @JoinColumn
    private User user;
}
