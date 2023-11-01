package com.demo.spring_transaction.service;

import java.util.Optional;

import com.demo.spring_transaction.model.BankAccount;

public interface BankAccountService {
	
	boolean isAccountExists(BankAccount bankAccount);
	
	BankAccount save(BankAccount bankAccount);
	
	Optional<BankAccount> findById(long accountId);
	
	Iterable<BankAccount> listAccounts();
	
	void deleteAccountById(long accountId);
}
