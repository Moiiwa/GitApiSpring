package gitapi.spring_github_agent.repositories;

import gitapi.spring_github_agent.tables.Committer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface CommitterRepository extends JpaRepository<Committer,Long> {
    @Query(value = "select * from Committer ",nativeQuery = true)
    List<Committer> findAllAuthors();
}
