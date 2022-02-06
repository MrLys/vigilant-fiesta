package xyz.lysggen.bankparser.repository;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
public class GenericRepository {
    private final WebApplicationContext appContext;

    Repositories repositories = null;

    public GenericRepository(WebApplicationContext appContext) {
        this.appContext = appContext;
        repositories = new Repositories(this.appContext);
    }
    public JpaRepository getRepository(AbstractPersistable entity) throws Exception {
        return (JpaRepository) repositories.getRepositoryFor(entity.getClass()).orElseThrow(Exception::new);
    }

}
