package xyz.lysggen.bankparser.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xyz.lysggen.bankparser.model.DatabaseModel;
import xyz.lysggen.bankparser.model.ManualTransaction;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
@Repository
public interface ManualTransactionRepository extends JpaRepository<ManualTransaction, Integer> {

    List<ManualTransaction> findAllByFinanceId(int financeId);
}
