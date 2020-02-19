package gitapi.spring_github_agent.repositories;

import gitapi.spring_github_agent.tables.Commit;
import gitapi.spring_github_agent.tables.Committer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommitsRepository extends JpaRepository<Commit,Long> {
    @Query(value = "select * from Commit ",nativeQuery = true)
    List<Committer> findAllAuthors();
}
