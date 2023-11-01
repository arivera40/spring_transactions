package com.demo.spring_transaction.controller;


import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring_transaction.model.BankAccount;
import com.demo.spring_transaction.service.BankAccountService;

@RestController
public class BankAccountController {

	private final BankAccountService accountService;
	
	public BankAccountController(final BankAccountService accountService) {
		this.accountService = accountService;
	}
	
	@PutMapping(path = "/accounts/{accountId}")
	public ResponseEntity<BankAccount> createUpdateAccount(
			@PathVariable final long accountId,
			@RequestBody final BankAccount account) {
		account.setAccountId(accountId);
		
		final boolean isAccountExists = accountService.isAccountExists(account);
		final BankAccount savedAccount = accountService.save(account);
		
		if (isAccountExists) {
			return new ResponseEntity<BankAccount>(savedAccount, HttpStatus.OK);
		} else {
			return new ResponseEntity<BankAccount>(savedAccount, HttpStatus.CREATED);
		}
	}
	
	@GetMapping(path = "/accounts/{accountId}")
	public ResponseEntity<BankAccount> retrieveAccount(@PathVariable final long accountId) {
		final Optional<BankAccount> foundAccount = accountService.findById(accountId);
		return foundAccount.map(account -> new ResponseEntity<BankAccount>(account, HttpStatus.OK))
				.orElse(new ResponseEntity<BankAccount>(HttpStatus.NOT_FOUND));
	}
	
	@GetMapping(path = "/accounts")
	public ResponseEntity<Iterable<BankAccount>> listAccounts() {
		return new ResponseEntity<Iterable<BankAccount>>(accountService.listAccounts(), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "accounts/{accountId}")
	public ResponseEntity deleteBook(@PathVariable final long accountId) {
		accountService.deleteAccountById(accountId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
