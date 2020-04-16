package gitapi.spring_github_agent.repositories;

import gitapi.spring_github_agent.tables.Githubcommit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommitsRepository extends JpaRepository<Githubcommit,Long> {
    @Query(value = "select * from Githubcommit ",nativeQuery = true)
    List<Githubcommit> findAllAuthors();
}
