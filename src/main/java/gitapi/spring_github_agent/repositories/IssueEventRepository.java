package gitapi.spring_github_agent.repositories;

import gitapi.spring_github_agent.tables.Issueevent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueEventRepository extends JpaRepository<Issueevent,Long> {
    @Query(value = "select * from Issueevent ",nativeQuery = true)
    List<Issueevent> findAllIssueEvents();
}
