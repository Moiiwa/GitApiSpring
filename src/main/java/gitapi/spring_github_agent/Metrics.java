package gitapi.spring_github_agent;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueEvent;
import org.kohsuke.github.GHRepository;

import javax.persistence.Entity;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Map;


public class Metrics {
    private GHRepository repository;
    private ArrayList<GHCommit> commits;
    private String repositoryName;
    private ArrayList<GHIssue> issues;
    private ArrayList<GHIssueEvent> issueEvents;
    public GHRepository getRepository(){
        return repository;
    }
    public void setIssueEvents(ArrayList<GHIssueEvent> issueEvents) {
        this.issueEvents = issueEvents;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public void setIssues(ArrayList<GHIssue> issueEvents) {
        this.issues = issueEvents;
    }

    public void setCommits(ArrayList<GHCommit> commits) {
        this.commits = commits;
    }

    public ArrayList<GHCommit> getCommits() {
        return commits;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public GHCommit getCommit(int i) {
        return commits.get(i);
    }

    public void setCommit(GHCommit commit) {
        commits.add(commit);
    }
}
