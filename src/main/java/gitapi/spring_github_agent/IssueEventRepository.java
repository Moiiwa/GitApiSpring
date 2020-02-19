package gitapi.spring_github_agent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueEventRepository extends JpaRepository<IssueEvent,Long> {
    @Query(value = "select * from IssueEvent ",nativeQuery = true)
    List<IssueEvent> findAllIssueEvents();
}
