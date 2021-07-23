package xyz.lysggen.bankparser.service;

import org.springframework.stereotype.Service;
import xyz.lysggen.bankparser.model.Category;
import xyz.lysggen.bankparser.model.Keyword;
import xyz.lysggen.bankparser.model.Transaction;
import xyz.lysggen.bankparser.repository.CategoryRepository;
import xyz.lysggen.bankparser.repository.KeywordRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryMatchMakingService {
    private final CategoryRepository categoryRepository;
    private final KeywordRepository keywordRepository;

    public CategoryMatchMakingService(CategoryRepository categoryRepository, KeywordRepository keywordRepository) {
        this.categoryRepository = categoryRepository;
        this.keywordRepository = keywordRepository;
    }
    private void createKeywords(String category, List<String> keywords) {
        keywords.stream().forEach(k -> {
            Keyword keyword = keywordRepository.getByName(k);
            if (keyword == null) {
                keyword = new Keyword();
                keyword.setValue(k);
                keyword.setCategory(categoryRepository.getByName(category));
                keywordRepository.save(keyword);
            }
        });
    }
    public List<Category> getCategorySuggestions(Transaction transaction) {
        String desc = transaction.getDescription();
        List<Category> suggestions = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        boolean anyMatch = false;
        for (Category category : categories) {
            if (keywordRepository
                    .getByCategoryId(category.getId())
                    .stream()
                    .anyMatch(keyword -> desc.contains(keyword.getValue()))) {
                anyMatch = true;
                suggestions.add(category);
            }
        }
        if (!anyMatch) {
            suggestions.add(categoryRepository.getByName("Diverse"));
        }
        return suggestions;
    }
    public List<Keyword> getKeywords(Transaction t) {
        String desc = t.getDescription();
        return keywordRepository.findAll().stream().filter(k -> desc.contains(k.getValue())).collect(Collectors.toUnmodifiableList());
    }
}
