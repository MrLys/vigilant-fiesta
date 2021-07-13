package xyz.lysggen.bankparser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.lysggen.bankparser.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
