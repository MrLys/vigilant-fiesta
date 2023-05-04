package xyz.lysggen.bankparser.controller;


import org.springframework.web.bind.annotation.*;
import xyz.lysggen.bankparser.model.Transaction;
import xyz.lysggen.bankparser.repository.TransactionRepository;

import java.util.List;

@RestController
public class TransactionController {
    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/transaction")
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @GetMapping("/transaction/{id}")
    public Transaction get(@PathVariable int id) {
        return transactionRepository.getById(id);
    }
    @PostMapping("/transaction")
    public Transaction create(@RequestParam("transaction") Transaction transaction) {
        transactionRepository.save(transaction);
        return transaction;
    }
}
