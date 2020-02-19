package gitapi.spring_github_agent.tables;

import org.kohsuke.github.GHCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@EnableAutoConfiguration
@Entity
@Table
public class Committer {
    public Committer(GHCommit.ShortInfo commit){
        date=commit.getCommitDate();
        name=commit.getAuthor().getName();
        email=commit.getAuthor().getEmail();
        message=commit.getMessage();
    }
    public Committer(){ }
    @Id
    @Column
    public Date date;
    @Column
    public String message;
    @Column
    public String name;

    @Column
    public String email;
}
