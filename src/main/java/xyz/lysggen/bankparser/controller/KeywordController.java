package xyz.lysggen.bankparser.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xyz.lysggen.bankparser.model.Keyword;
import xyz.lysggen.bankparser.repository.KeywordRepository;

import java.util.List;

@RestController
public class KeywordController {
    private final KeywordRepository keywordRepository;

    public KeywordController(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    @GetMapping("/keyword")
    public List<Keyword> getAll() {
        return keywordRepository.findAll();
    }

    @GetMapping("/keyword/{id}")
    public Keyword get(@PathVariable int id) {
        return keywordRepository.getById(id);
    }

}
