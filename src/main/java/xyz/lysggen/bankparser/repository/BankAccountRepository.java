package xyz.lysggen.bankparser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.lysggen.bankparser.model.Account;

public interface BankAccountRepository extends JpaRepository<Account, Integer> {
}
