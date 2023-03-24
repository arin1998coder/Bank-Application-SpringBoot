package com.BankAccounts.Bank.Accounts.Controller;

import com.BankAccounts.Bank.Accounts.Repository.UserRepository;
import com.BankAccounts.Bank.Accounts.Service.UserService;
import com.BankAccounts.Bank.Accounts.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    //create's user and opens account for them
    @PostMapping("/create")
    public String createUser(@RequestBody List<User> userList){

       return userService.createUser(userList);

    }
}
