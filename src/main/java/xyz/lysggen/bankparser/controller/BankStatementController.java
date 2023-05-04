package xyz.lysggen.bankparser.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.lysggen.bankparser.model.BankAccount;
import xyz.lysggen.bankparser.model.BankStatement;
import xyz.lysggen.bankparser.repository.BankAccountRepository;
import xyz.lysggen.bankparser.repository.BankStatementRepository;
import xyz.lysggen.bankparser.repository.TransactionRepository;
import xyz.lysggen.bankparser.service.BankDataParser;
import xyz.lysggen.bankparser.service.CategoryMatchMakingService;
import xyz.lysggen.bankparser.service.PdfParserService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class BankStatementController {
    private final BankDataParser bankDataParser;
    private final BankStatementRepository bankStatementRepository;
    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final CategoryMatchMakingService matchMakingService;
    private final BankDataParser dataParser;
    private final PdfParserService pdfParserService;

    public BankStatementController(BankDataParser bankDataParser,
                                   BankStatementRepository bankStatementRepository,
                                   TransactionRepository transactionRepository,
                                   BankAccountRepository bankAccountRepository,
                                   CategoryMatchMakingService matchMakingService,
                                   BankDataParser dataParser,
                                   PdfParserService pdfParserService) {
        this.bankDataParser = bankDataParser;
        this.bankStatementRepository = bankStatementRepository;
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.matchMakingService = matchMakingService;
        this.dataParser = dataParser;
        this.pdfParserService = pdfParserService;
    }

    @GetMapping("/statement/{id}")
    public BankStatement get (
            @PathVariable int id) {
        BankStatement bankStatement = bankStatementRepository.getById(id);
        bankStatement.setTransactions(transactionRepository.getByBankStatementId(id));
        if (bankAccountRepository.getByAccount(bankStatement.getAccount()) == null) {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setName(bankStatement.getName());
            bankAccount.setAccountNumber(bankStatement.getAccount());
            bankAccountRepository.save(bankAccount);
        }
        bankStatement.getTransactions().stream().forEach(t -> {
            t.setPossibleCategories(matchMakingService.getCategorySuggestions(t));
            t.setKeywords(matchMakingService.getKeywords(t));
        });
        return bankStatement;
    }

    @PostMapping("/statement/")
    public BankStatement handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            BankStatement bankStatement = bankDataParser.readData(new InputStreamReader(file.getInputStream()));
            bankStatementRepository.save(bankStatement);
            bankStatement.getTransactions().forEach(t -> {
                        t.setBankStatement(bankStatement);
                        transactionRepository.save(t);
                    });
            return bankStatement;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @PostMapping("/statementPdf/")
    public BankStatement handleFileUploadPdf(@RequestParam("file") MultipartFile file) throws IOException {
        File directory = new File(".");
        File dest = new File(directory.getCanonicalPath() + File.separator + "tmp_statement.pdf");
        try {
            file.transferTo(dest);
            pdfParserService.executeCommands(dest);
            File output = new File(directory.getCanonicalPath() + File.separator +"out.txt");
            try (InputStreamReader isr = new InputStreamReader(new FileInputStream(output))) {
                BankStatement bankStatement = bankDataParser.readData(isr);
                bankStatementRepository.save(bankStatement);
                bankStatement.getTransactions().forEach(t -> {
                    t.setBankStatement(bankStatement);
                    transactionRepository.save(t);
                });
                return bankStatement;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //if(dest.delete()) {
            //    System.out.println("it worked");
            //} else {
            //    System.out.println("it didnt work");
            //}
        }
        return null;
    }

}
