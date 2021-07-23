package xyz.lysggen.bankparser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.lysggen.bankparser.model.Keyword;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Integer> {
    @Query("select k from Keyword k where k.value = ?1")
    Keyword getByName(String name);
    @Query("select k from Keyword k where k.category.id = ?1")
    List<Keyword> getByCategoryId(int categoryId);
    @Query("select k from Keyword k where k.category.name= ?1")
    List<Keyword> getByCategoryName(String categoryName);

}

