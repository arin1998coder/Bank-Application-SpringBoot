package com.BankAccounts.Bank.Accounts.Service;

import com.BankAccounts.Bank.Accounts.Repository.AccountRepository;
import com.BankAccounts.Bank.Accounts.Repository.UserRepository;
import com.BankAccounts.Bank.Accounts.entity.Account;
import com.BankAccounts.Bank.Accounts.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountService accountService;
    public String createUser(List<User> userList){

        //create account for the users
       Account account=accountService.createAccount(userList);

       //set account num to every user
        for(User user:userList){
            user.setAccount(account);
        }
        //save account
        accountRepository.save(account);

       return "account opened successfully with account no: "+account.getAccountNo();

    }
}
