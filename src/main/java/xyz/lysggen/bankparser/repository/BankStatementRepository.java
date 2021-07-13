package xyz.lysggen.bankparser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.lysggen.bankparser.model.BankStatement;

public interface BankStatementRepository extends JpaRepository<BankStatement, Integer> {
}
