package com.money.management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.money.management.entity.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}