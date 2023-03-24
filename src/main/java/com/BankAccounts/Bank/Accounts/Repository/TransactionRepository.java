package com.BankAccounts.Bank.Accounts.Repository;

import com.BankAccounts.Bank.Accounts.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer>{

}
