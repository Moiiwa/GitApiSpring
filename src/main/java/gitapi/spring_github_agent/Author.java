package gitapi.spring_github_agent;

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
public class Author {
    public Author(GHCommit.ShortInfo commit){
        date=commit.getCommitDate();
        name=commit.getAuthor().getName();
        email=commit.getAuthor().getEmail();
    }
    public Author(){ }
    @Column
    public Date date;
    @Column
    public String name;
    @Id
    @Column
    public String email;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}
