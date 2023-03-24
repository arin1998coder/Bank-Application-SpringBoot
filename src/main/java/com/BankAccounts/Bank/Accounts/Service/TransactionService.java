package com.BankAccounts.Bank.Accounts.Service;

import com.BankAccounts.Bank.Accounts.Enums.TransactionStatus;
import com.BankAccounts.Bank.Accounts.Enums.TransactionType;
import com.BankAccounts.Bank.Accounts.Repository.AccountRepository;
import com.BankAccounts.Bank.Accounts.Repository.TransactionRepository;
import com.BankAccounts.Bank.Accounts.Repository.UserRepository;
import com.BankAccounts.Bank.Accounts.dto.RequestDTO;
import com.BankAccounts.Bank.Accounts.dto.ResponseDTO;
import com.BankAccounts.Bank.Accounts.entity.Account;
import com.BankAccounts.Bank.Accounts.entity.Transaction;
import com.BankAccounts.Bank.Accounts.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;


    //takes in multiple transaction(credit and debit) requests in a List at the same time and processes them in FIFO manner
    @Transactional
    public List<ResponseDTO> transaction(List<RequestDTO> requestDTOs){

        List<ResponseDTO> transactionLogs = new ArrayList<>();

        for(RequestDTO requestDTO : requestDTOs){
            if(requestDTO.getTransactionType()==TransactionType.DEPOSIT){
                transactionLogs.add(credit(requestDTO));
            }
            else{
                transactionLogs.add(debit(requestDTO));
            }
        }

        return transactionLogs;
    }

    @Transactional
    public ResponseDTO credit(RequestDTO requestDTO)   {

            ResponseDTO responseDTO = new ResponseDTO();

            User user;
            Account account;

            //create transaction object
            Transaction transaction = new Transaction();

            transaction.setAmount(requestDTO.getAmount());
            transaction.setTransactionType(TransactionType.DEPOSIT);

            //check if userId is valid or not
            try {
                user = userRepository.findById(requestDTO.getUserId()).get();
            } catch (Exception e) {
                transaction.setTransactionStatus(TransactionStatus.FAILED);
                transactionRepository.save(transaction);
                responseDTO.setTid(transaction.getId());
                responseDTO.setAmount(requestDTO.getAmount());
                responseDTO.setUserId(requestDTO.getUserId());
                responseDTO.setBalance("NA");
                responseDTO.setMessage("User not Found");
                System.out.println("User Not Found");
                return responseDTO;
            }

            account = user.getAccount();

            //at this line , i know account and user are valid

            //check deposit amount should not be negative
            if (requestDTO.getAmount() < 0) {
                transaction.setTransactionStatus(TransactionStatus.FAILED);
                transaction.setAccount(account);
                transaction.setUser(user);
                account.getTransactionList().add(transaction);
                user.getTransactionList().add(transaction);
                transactionRepository.save(transaction);
                responseDTO.setTid(transaction.getId());
                responseDTO.setAmount(requestDTO.getAmount());
                responseDTO.setUserId(requestDTO.getUserId());
                responseDTO.setAccId(account.getAccountId());
                responseDTO.setBalance(String.valueOf(account.getBalance()));
                responseDTO.setMessage("Can't Deposit ! Deposit amount is negative");
                System.out.println("Deposit amount is negative");
                return responseDTO;
            }

            //handle the case if account balance goes beyond 10million
            double currBalance = account.getBalance();
            double newBalance = account.getBalance() + requestDTO.getAmount();

            if (newBalance > 1e7) {
                transaction.setTransactionStatus(TransactionStatus.FAILED);
                transaction.setAccount(account);
                transaction.setUser(user);
                account.getTransactionList().add(transaction);
                user.getTransactionList().add(transaction);
                transactionRepository.save(transaction);
                responseDTO.setTid(transaction.getId());
                responseDTO.setAmount(requestDTO.getAmount());
                responseDTO.setUserId(requestDTO.getUserId());
                responseDTO.setAccId(account.getAccountId());
                responseDTO.setBalance(String.valueOf(currBalance));
                responseDTO.setMessage("Can't Deposit ! Account balance is going beyond 10million");
                System.out.println("Account balance is going beyond 10million");
                return responseDTO;
//
            }

            //deposit the amount to the user's account and set the attributes accordinly

            transaction.setTransactionStatus(TransactionStatus.SUCCESS);

            account.setBalance(newBalance);
            transaction.setAccount(account);
            transaction.setUser(user);
            account.getTransactionList().add(transaction);
            user.getTransactionList().add(transaction);

            transactionRepository.save(transaction);

            responseDTO.setTid(transaction.getId());
            responseDTO.setAmount(requestDTO.getAmount());
            responseDTO.setUserId(requestDTO.getUserId());
            responseDTO.setAccId(account.getAccountId());
            responseDTO.setBalance(String.valueOf(newBalance));
            responseDTO.setMessage("Amount Deposited Successfully");

           return responseDTO;


    }

    @Transactional
    public ResponseDTO debit(RequestDTO requestDTO) {

        User user;
        Account account;

        ResponseDTO responseDTO = new ResponseDTO();

        //create transaction object
        Transaction transaction = new Transaction();

        transaction.setAmount(requestDTO.getAmount());
        transaction.setTransactionType(TransactionType.WITHDRAW);

        //check if userId is valid or not
        try{
            user = userRepository.findById(requestDTO.getUserId()).get();
        }
        catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            responseDTO.setTid(transaction.getId());
            responseDTO.setAmount(requestDTO.getAmount());
            responseDTO.setUserId(requestDTO.getUserId());
            responseDTO.setBalance("NA");
            responseDTO.setMessage("User not Found");
            System.out.println("User Not Found");
            return responseDTO;
        }

        account = user.getAccount();
        //at this line , we know account and user are valid

        //check amount to be withdrawn should not be -ve
        if(requestDTO.getAmount()<0){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setAccount(account);
            transaction.setUser(user);
            account.getTransactionList().add(transaction);
            user.getTransactionList().add(transaction);
            transactionRepository.save(transaction);
            responseDTO.setTid(transaction.getId());
            responseDTO.setAmount(requestDTO.getAmount());
            responseDTO.setUserId(requestDTO.getUserId());
            responseDTO.setAccId(account.getAccountId());
            responseDTO.setBalance(String.valueOf(account.getBalance()));
            responseDTO.setMessage("Cant Withdraw ! Withdraw amount is negative");
            System.out.println("Withdraw amount is negative");
            return responseDTO;
        }

        //check if balance is sufficient for withdrawal, then withdraw should be blocked

        double currBalance = account.getBalance();
        double newBalance = currBalance - requestDTO.getAmount();

        if(newBalance < 0){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setAccount(account);
            transaction.setUser(user);
            account.getTransactionList().add(transaction);
            user.getTransactionList().add(transaction);
            transactionRepository.save(transaction);
            responseDTO.setTid(transaction.getId());
            responseDTO.setAmount(requestDTO.getAmount());
            responseDTO.setUserId(requestDTO.getUserId());
            responseDTO.setAccId(account.getAccountId());
            responseDTO.setMessage("Can't withdraw ! You dont have sufficient balance");
            responseDTO.setBalance(String.valueOf(account.getBalance()));

            return responseDTO;
        }

        //user can withdraw the required amaount , set attributes after withdrawal properly
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        account.setBalance(newBalance);
        transaction.setAccount(account);
        transaction.setUser(user);
        account.getTransactionList().add(transaction);
        user.getTransactionList().add(transaction);

        transactionRepository.save(transaction);

        responseDTO.setTid(transaction.getId());
        responseDTO.setAmount(requestDTO.getAmount());
        responseDTO.setUserId(requestDTO.getUserId());
        responseDTO.setAccId(account.getAccountId());
        responseDTO.setMessage("Withdrawal Successfull");
        responseDTO.setBalance(String.valueOf(account.getBalance()));

        return responseDTO;


    }
}
