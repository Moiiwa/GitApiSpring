package gitapi.spring_github_agent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue,Long> {

}
