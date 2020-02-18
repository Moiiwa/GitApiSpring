package gitapi.spring_github_agent;

import org.kohsuke.github.GHIssue;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.IOException;
import java.util.Date;

@EnableAutoConfiguration
@Entity
@Table
public class Issue
{
    @Id
    String htmlUrl;
    @Column
    String assignee;
    @Column
    String state;
    @Column
    Date closedAt;
    @Column
    String body;
    @Column
    String title;
    @Column
    Date createdAt;
    @Column
    Date updatedAt;
    public Issue(GHIssue ghIssue) throws IOException {
        htmlUrl=ghIssue.getHtmlUrl().toString();
        createdAt=ghIssue.getCreatedAt();
        updatedAt=ghIssue.getUpdatedAt();
        title=ghIssue.getTitle();
        body=ghIssue.getBody();
        closedAt=ghIssue.getClosedAt();
        state=ghIssue.getState().toString();
        if(ghIssue.getAssignee()!=null)
        assignee=ghIssue.getAssignee().getEmail();
    }
    public Issue(){}
}
