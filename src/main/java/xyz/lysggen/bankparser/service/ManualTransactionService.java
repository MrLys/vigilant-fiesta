package xyz.lysggen.bankparser.service;

import org.springframework.stereotype.Service;
import xyz.lysggen.bankparser.dto.ManualTransactionDTO;
import xyz.lysggen.bankparser.model.Finance;
import xyz.lysggen.bankparser.model.ManualTransaction;
import xyz.lysggen.bankparser.repository.FinanceRepository;
import xyz.lysggen.bankparser.repository.ManualTransactionRepository;

import java.time.LocalDateTime;

@Service
public class ManualTransactionService {

    private final FinanceRepository financeRepository;
    private final ManualTransactionRepository manualTransactionRepository;

    public ManualTransactionService(FinanceRepository financeRepository, ManualTransactionRepository manualTransactionRepository) {
        this.financeRepository = financeRepository;
        this.manualTransactionRepository = manualTransactionRepository;
    }

    public ManualTransaction toTransaction(ManualTransactionDTO dto) {
        ManualTransaction transaction = new ManualTransaction();
        transaction.setAmount(dto.getAmount());
        transaction.setDescription(dto.getDescription());
        transaction.setDate(LocalDateTime.now());
        transaction.setFinance(financeRepository.getById(dto.getFinance()));
        System.out.println(dto.getFinance());
        return transaction;
    }

    public ManualTransaction map(int id, ManualTransactionDTO dto) {
        ManualTransaction mt = manualTransactionRepository.getById(id);
        if (dto.getAmount() != -1) {
            mt.setAmount(dto.getAmount());
        }
        if (dto.getFinance() != -1) {
            Finance finance = financeRepository.getById(dto.getFinance());
            mt.setFinance(finance);
        }
        if (!dto.getDescription().equals("")) {
            mt.setDescription(dto.getDescription());
        }
        return mt;
    }
}
