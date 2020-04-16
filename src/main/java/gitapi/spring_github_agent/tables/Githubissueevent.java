package gitapi.spring_github_agent.tables;

import org.kohsuke.github.GHIssueEvent;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@EnableAutoConfiguration
@Entity
@Table
public class Githubissueevent {
    @Column
    public String repositoryName;
    @Id
    @Column
    public Long id;
    @Column
    public Date createdAt;
    @Column
    public String event;
    @Column
    public String userLogin;
    @Column
    public String issueHtml;

    public Githubissueevent(GHIssueEvent event, String repositoryName){
        this.repositoryName=repositoryName;
        this.id=event.getId();
        createdAt=event.getCreatedAt();
        this.event=event.getEvent();
        userLogin=event.getActor().getLogin();
        issueHtml=event.getIssue().getHtmlUrl().toString();
    }
    public Githubissueevent(){}
}