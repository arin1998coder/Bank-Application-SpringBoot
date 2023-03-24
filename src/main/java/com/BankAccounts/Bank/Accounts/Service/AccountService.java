package com.BankAccounts.Bank.Accounts.Service;

import com.BankAccounts.Bank.Accounts.Repository.AccountRepository;
import com.BankAccounts.Bank.Accounts.entity.Account;
import com.BankAccounts.Bank.Accounts.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {


    //opening joint account for the multiple users(can be 1 too)
    public Account createAccount(List<User> userList){

        //create account abject
        Account account = new Account();

        //set its attributes
        account.setAccountNo(UUID.randomUUID().toString());
        account.setBalance(0);

        //link all the users who wanna open acc together to this account
        account.getUserList().addAll(userList);

        return account;

    }
}
