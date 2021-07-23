package xyz.lysggen.bankparser.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.lysggen.bankparser.model.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("SELECT t FROM transaction t WHERE t.bankStatement.id = ?1")
    List<Transaction> getByBankStatementId(int id);
}

