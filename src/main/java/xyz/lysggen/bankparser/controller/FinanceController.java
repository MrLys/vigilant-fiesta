package xyz.lysggen.bankparser.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.lysggen.bankparser.model.Finance;
import xyz.lysggen.bankparser.repository.FinanceRepository;

@RestController
public class FinanceController {
    private final FinanceRepository financeRepository;

    public FinanceController(FinanceRepository financeRepository) {
        this.financeRepository = financeRepository;
    }

    @GetMapping("/finance/{id}")
    public Finance getById(@PathVariable int id) {
       return financeRepository.getById(id);
    }
}
