package com.demo.spring_transaction.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.demo.spring_transaction.model.BankAccount;
import com.demo.spring_transaction.model.User;
import com.demo.spring_transaction.repository.BankAccountRepository;
import com.demo.spring_transaction.repository.UserRepository;
import com.demo.spring_transaction.service.BankAccountService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

	private final BankAccountRepository accountRepository;
	
	private final UserRepository userRepository;
	
	public BankAccountServiceImpl(
			final BankAccountRepository accountRepository,
			final UserRepository userRepository) {
		this.accountRepository = accountRepository;
		this.userRepository = userRepository;
	}
	
	@Transactional
	@Override
	public BankAccount save(final BankAccount account) {
		if (account.getUser() == null) {
			throw new RuntimeException("Account details must be provided.");
		}
		final User accountUser = account.getUser();
		
		account.setUser(null);
		
        final BankAccount savedAccount = accountRepository.save(account);
        
        if (accountUser.getUserId() == null) {
            final User user = userRepository.save(accountUser);
            savedAccount.setUser(user);
        } else {
            final User user = userRepository.findById(accountUser.getUserId())
            .orElseThrow(() -> new RuntimeException("Author not found"));
            savedAccount.setUser(user);
        }

		return savedAccount;
	}

	@Override
	public boolean isAccountExists(BankAccount account) {
		return accountRepository.existsById(account.getAccountId());
	}

	@Override
	public Optional<BankAccount> findById(long accountId) {
		return accountRepository.findById(accountId);
	}

	@Override
	public Iterable<BankAccount> listAccounts() {
		return accountRepository.findAll();
	}

	@Override
	public void deleteAccountById(long accountId) {
        try {
            accountRepository.deleteById(accountId);

        } catch(final EmptyResultDataAccessException ex) {
            log.debug("Attempted to delete non-existing account", ex);
        }
	}
}
