package gitapi.spring_github_agent.repositories;

import gitapi.spring_github_agent.tables.Githubissueevent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueEventRepository extends JpaRepository<Githubissueevent,Long> {
    @Query(value = "select * from Githubissueevent ",nativeQuery = true)
    List<Githubissueevent> findAllIssueEvents();
}
