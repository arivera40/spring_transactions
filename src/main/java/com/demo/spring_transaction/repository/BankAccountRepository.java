package com.demo.spring_transaction.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.spring_transaction.model.BankAccount;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
}
