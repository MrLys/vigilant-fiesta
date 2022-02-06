package xyz.lysggen.bankparser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.lysggen.bankparser.model.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
    @Query("select a from BankAccount a where a.accountNumber = ?1")
    BankAccount getByAccount(String account);
}
