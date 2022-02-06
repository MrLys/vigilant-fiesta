package xyz.lysggen.bankparser.controller;

import org.springframework.web.bind.annotation.*;
import xyz.lysggen.bankparser.dto.ManualTransactionDTO;
import xyz.lysggen.bankparser.model.ManualTransaction;
import xyz.lysggen.bankparser.model.Transaction;
import xyz.lysggen.bankparser.repository.ManualTransactionRepository;
import xyz.lysggen.bankparser.repository.TransactionRepository;
import xyz.lysggen.bankparser.service.ManualTransactionService;
import xyz.lysggen.bankparser.service.TransactionService;

import java.util.List;
import java.util.Map;

@RestController
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;
    private final ManualTransactionRepository manualTransactionRepository;
    private final ManualTransactionService manualTransactionService;

    public TransactionController(TransactionRepository transactionRepository,
                                 TransactionService transactionService, ManualTransactionRepository manualTransactionRepository, ManualTransactionService manualTransactionService) {
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
        this.manualTransactionRepository = manualTransactionRepository;
        this.manualTransactionService = manualTransactionService;
    }

    @GetMapping("/transaction/all")
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @PostMapping(value = "/transaction", consumes = "application/json")
    public void create(@RequestBody ManualTransactionDTO dto) {
        transactionRepository.save(transactionService.toTransaction(dto));
    }

    @PostMapping(value = "/manualtransaction", consumes = "application/json")
    public void createManual(@RequestBody ManualTransactionDTO dto) {
        manualTransactionRepository.save(manualTransactionService.toTransaction(dto));
    }

    @GetMapping("/manualtransaction")
    public List<ManualTransaction> getAllByFinanceId(@RequestParam int financeId) {
        return manualTransactionRepository.findAllByFinanceId(financeId);
    }

    @PatchMapping("/manualtransaction/{id}")
    public void partialUpdateTransaction(@RequestBody ManualTransactionDTO dto,
                                         @PathVariable int id) {
        manualTransactionRepository.save(manualTransactionService.map(id, dto));
    }
}
