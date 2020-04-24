package gitapi.spring_github_agent.tables;

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
public class Githubissue
{
    @Column
    public String repositoryName;
    @Id
    public String htmlUrl;
    @Column
    public String assignee;
    @Column
    public String state;
    @Column
    public Date closedAt;
    @Column
    public String body;
    @Column
    public String title;
    @Column
    public Date createdAt;
    @Column
    public Date updatedAt;
    public Githubissue(GHIssue ghIssue, String repositoryName) throws IOException {
        this.repositoryName=repositoryName;
        htmlUrl=ghIssue.getHtmlUrl().toString();
        createdAt=ghIssue.getCreatedAt();
        updatedAt=ghIssue.getUpdatedAt();
        title=ghIssue.getTitle();
        body=ghIssue.getBody();
        closedAt=ghIssue.getClosedAt();
        state=ghIssue.getState().toString();

        if(ghIssue.getUser()!=null)
        assignee=ghIssue.getUser().getEmail();
    }
    public Githubissue(){}
}
