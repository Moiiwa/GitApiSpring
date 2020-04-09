package gitapi.spring_github_agent.repositories;

import gitapi.spring_github_agent.tables.Githubissue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueRepository extends JpaRepository<Githubissue,Long> {
    @Query(value = "select * from Githubissue ",nativeQuery = true)
    List<Githubissue> findAllIssues();
}
