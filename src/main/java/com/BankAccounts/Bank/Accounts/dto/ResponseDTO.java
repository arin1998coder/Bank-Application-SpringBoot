package com.BankAccounts.Bank.Accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    private int tid;

    private int accId;

    private int userId;

    private double amount;

    private String balance;

    private String message;
}
