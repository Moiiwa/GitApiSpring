package gitapi.spring_github_agent.tables;

import org.kohsuke.github.GHCommit;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;
@EnableAutoConfiguration
@Entity
@Table
public class Commit {
    @Column
    public Date date;
    @Column
    public String message;
    @Column
    public String email;
    @Id
    @Column
    public String html_url;
    @Column
    public String parent1;
    @Column
    public String parent2;

    public Commit(GHCommit commit) throws IOException {
        GHCommit.ShortInfo shortInfo=commit.getCommitShortInfo();
        date=commit.getCommitShortInfo().getCommitDate();
        message=commit.getCommitShortInfo().getMessage();
        email=commit.getCommitShortInfo().getAuthor().getEmail();
        html_url=commit.getHtmlUrl().toString();
        List<GHCommit> parents=commit.getParents();
        if(parents.size()==1){
            Commit parent=new Commit(parents.get(0));
            parent1=parent.html_url;
        }
        if(parents.size()==2){
            Commit parent1=new Commit(parents.get(0));
            this.parent1=parent1.html_url;
            Commit parent2=new Commit(parents.get(1));
            this.parent2=parent2.html_url;
        }
    }
    public Commit(){

    }
}
