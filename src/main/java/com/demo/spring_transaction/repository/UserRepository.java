package com.demo.spring_transaction.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.spring_transaction.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
