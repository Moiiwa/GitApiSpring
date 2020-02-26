package gitapi.spring_github_agent.repositories;

import gitapi.spring_github_agent.tables.Issue;
import gitapi.spring_github_agent.tables.Issueevent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue,Long> {
    @Query(value = "select * from Issue ",nativeQuery = true)
    List<Issue> findAllIssues();
}
