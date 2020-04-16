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
public class Githubcommit {
    @Column
    public String repositoryName;
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

    public Githubcommit(GHCommit commit,String repositoryName) throws IOException {
        this.repositoryName=repositoryName;
        GHCommit.ShortInfo shortInfo=commit.getCommitShortInfo();
        date=commit.getCommitShortInfo().getCommitDate();
        message=commit.getCommitShortInfo().getMessage();
        email=commit.getCommitShortInfo().getAuthor().getEmail();
        html_url=commit.getHtmlUrl().toString();
        List<GHCommit> parents=commit.getParents();
        if(parents.size()==1){
            Githubcommit parent=new Githubcommit(parents.get(0),repositoryName);
            parent1=parent.html_url;
        }
        if(parents.size()==2){
            Githubcommit parent1=new Githubcommit(parents.get(0),repositoryName);
            this.parent1=parent1.html_url;
            Githubcommit parent2=new Githubcommit(parents.get(1),repositoryName);
            this.parent2=parent2.html_url;
        }
    }
    public Githubcommit(){

    }
}
