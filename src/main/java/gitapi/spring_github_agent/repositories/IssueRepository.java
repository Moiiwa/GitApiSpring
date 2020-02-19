package gitapi.spring_github_agent.repositories;

import gitapi.spring_github_agent.tables.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue,Long> {

}
