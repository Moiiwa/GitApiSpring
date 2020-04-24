package gitapi.spring_github_agent.tables;

import gitapi.spring_github_agent.Connector;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.awt.*;
import java.io.IOException;
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
    public String userlogin;
    @Column
    public String userEmail;
    @Column
    public String issueHtml;


    public Githubissueevent(GHIssueEvent event, String repositoryName) throws IOException {
        this.repositoryName=repositoryName;
        this.id=event.getId();
        createdAt=event.getCreatedAt();
        this.event=event.getEvent();
        userlogin =event.getActor().getLogin();
        userEmail =event.getActor().getEmail();
        issueHtml=event.getIssue().getHtmlUrl().toString();
    }
    public Githubissueevent(){}
}
