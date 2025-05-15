package com.money.management.resource;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.money.management.entity.Transaction;
import com.money.management.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionResource {
    
    private final TransactionService transactionService;

    @Autowired
    public TransactionResource(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.findAll();
    }
    
    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.save(transaction);
    }
    
    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable String id) {
        return transactionService.findById(id);
    }
    
    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable String id) {
        transactionService.deleteById(id);
    }
}