package xyz.lysggen.bankparser.controller;

import org.apache.logging.log4j.util.Strings;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import xyz.lysggen.bankparser.model.Category;
import xyz.lysggen.bankparser.repository.CategoryRepository;

import java.util.List;

@RestController
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/category")
    public List<Category> getAll(@RequestParam("name") @Nullable String name) {
        if (Strings.isBlank(name)) {
            return List.of(categoryRepository.getByName(name));
        }
        return categoryRepository.findAll();
    }
    @GetMapping("/category/{id}")
    public Category get(@PathVariable int id) {
        return categoryRepository.getById(id);
    }
    @PostMapping("/category")
    public Category create(@RequestParam Category category) {
        categoryRepository.save(category);
       return category;
    }

}
