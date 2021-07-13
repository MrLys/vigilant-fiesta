package xyz.lysggen.bankparser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.lysggen.bankparser.model.DataRow;

public interface DataRowRepository extends JpaRepository<DataRow, Integer> {
}
