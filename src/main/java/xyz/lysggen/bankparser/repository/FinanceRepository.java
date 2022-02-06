package xyz.lysggen.bankparser.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import xyz.lysggen.bankparser.model.Finance;

public interface FinanceRepository  extends JpaRepository<Finance, Integer> {
}
