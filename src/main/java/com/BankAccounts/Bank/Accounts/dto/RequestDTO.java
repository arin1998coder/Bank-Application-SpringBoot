package com.BankAccounts.Bank.Accounts.dto;

import com.BankAccounts.Bank.Accounts.Enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {

    private int userId;

    private double amount;

    private TransactionType transactionType;
}

