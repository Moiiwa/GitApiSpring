package gitapi.spring_github_agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
public interface AuthorRepository extends JpaRepository<Author,Long> {
    @Query(value = "select * from Author ",nativeQuery = true)
    List<Author> findAllAuthors();
}
