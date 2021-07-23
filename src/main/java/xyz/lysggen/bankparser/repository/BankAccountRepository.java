package xyz.lysggen.bankparser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.lysggen.bankparser.model.Account;

public interface BankAccountRepository extends JpaRepository<Account, Integer> {
    @Query("select a from Account a where a.accountNumber = ?1")
    Account getByAccount(String account);
}
