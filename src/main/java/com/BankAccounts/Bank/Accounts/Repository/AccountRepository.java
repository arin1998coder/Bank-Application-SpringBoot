package com.BankAccounts.Bank.Accounts.Repository;

import com.BankAccounts.Bank.Accounts.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {


}
