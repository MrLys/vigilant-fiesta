package xyz.lysggen.bankparser.service;

import org.springframework.stereotype.Service;
import xyz.lysggen.bankparser.dto.ManualTransactionDTO;
import xyz.lysggen.bankparser.model.Category;
import xyz.lysggen.bankparser.model.Transaction;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    public void assignCategory(Transaction row, Category category) {
        row.setCategory(category);
    }

   public Transaction toTransaction(ManualTransactionDTO dto) {
        Transaction transaction = new Transaction();
        if (dto.getAmount() > 0) {
            transaction.setAmountIn(dto.getAmount());
        } else {
            transaction.setAmountOut(dto.getAmount());
        }
        transaction.setDescription(dto.getDescription());
        String date = LocalDateTime.now().toString();
        transaction.setPostedDate(date);
       transaction.setDate(date);
       return transaction;
   }
}
