package com.BankAccounts.Bank.Accounts.Repository;

import com.BankAccounts.Bank.Accounts.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
