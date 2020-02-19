package gitapi.spring_github_agent;

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
public class IssueEvent {
    @Id
    @Column
    Long id;
    @Column
    Date createdAt;
    @Column
    String event;
    @Column
    String userLogin;
    @Column
    String issueHtml;

    public IssueEvent(GHIssueEvent event){
        this.id=event.getId();
        createdAt=event.getCreatedAt();
        this.event=event.getEvent();
        userLogin=event.getActor().getLogin();
        issueHtml=event.getIssue().getHtmlUrl().toString();
    }
    public IssueEvent(){}
}
