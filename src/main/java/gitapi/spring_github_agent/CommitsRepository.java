package gitapi.spring_github_agent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommitsRepository extends JpaRepository<Commit,Long> {
    @Query(value = "select * from Commit ",nativeQuery = true)
    List<Committer> findAllAuthors();
}
