package xyz.lysggen.bankparser.service;

import xyz.lysggen.bankparser.model.Category;
import xyz.lysggen.bankparser.model.Transaction;

public class TransactionService {

    public void assignCategory(Transaction row, Category category) {
        row.setCategory(category);
    }
}
