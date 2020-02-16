package gitapi.spring_github_agent;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class Connector {
    String token="7c501c8695faa5e067c8754faeff797706fb8710";
    private static int numOfRepos;
    public Metrics metrics[];
    public void connect() throws IOException {

        GitHub gitHub=connect(token);
        ArrayList repositories=getRepositories(gitHub);
        setNumOfRepos(repositories.size());
        metrics=new Metrics[getNumOfRepos()];
        initializeMetrics(metrics);
        setRepoNames(metrics,repositories);
        setCommits(metrics,repositories);
        setIssues(metrics,repositories);
        setIssueEvents(metrics,repositories);
        System.out.println();

    }

    @Autowired
    private CommitterService committerService;

    @Autowired
    public CommitService commitService;

    public static void initializeMetrics(Metrics[] metrics){
        for (int i=0;i<getNumOfRepos();i++){
            metrics[i]=new Metrics();
        }
    }

    public void setCommits(Metrics[] metrics,ArrayList<GHRepository> repositories) throws IOException {
        for(int i=0;i<getNumOfRepos();++i){
            metrics[i].setCommits(getCommits(repositories.get(i)));
            for(int j=0;j<metrics[i].getCommits().size();j++){

                Commit commit=new Commit(metrics[i].getCommit(j));
                Committer committer=new Committer(metrics[i].getCommit(j).getCommitShortInfo());
                committerService.createCommitter(committer);
                commitService.createCommit(commit);
            }
        }
    }


    public static ArrayList getCommits(GHRepository repository){
        ArrayList<GHCommit> commits;
        commits=(ArrayList<GHCommit>) repository.listCommits().asList();
        return commits;
    }

    public static ArrayList getIssues(GHRepository repository) throws IOException {
        ArrayList<GHIssue> issues;
        issues=(ArrayList<GHIssue>) repository.listIssues(GHIssueState.ALL).asList();
        return issues;
    }

    public static void setIssues(Metrics[] metrics,ArrayList<GHRepository> repositories) throws IOException {
        for(int i=0;i<getNumOfRepos();++i){
            metrics[i].setIssueEvents(getIssueEvents(repositories.get(i)));
        }
    }


    public static ArrayList getIssueEvents(GHRepository repository) throws IOException {
        ArrayList<GHIssueEvent> issues;
        issues=(ArrayList<GHIssueEvent>) repository.listIssueEvents().asList();
        return issues;
    }

    public static void setIssueEvents(Metrics[] metrics,ArrayList<GHRepository> repositories) throws IOException {
        for(int i=0;i<getNumOfRepos();++i){
            metrics[i].setIssues(getIssues(repositories.get(i)));
        }
    }

    public static void setRepoNames(Metrics[] metrics,ArrayList<GHRepository> repositories){
        for(int i=0;i<getNumOfRepos();++i){
            metrics[i].setRepositoryName(repositories.get(i).getName());
        }
    }
    public static int getNumOfRepos(){
        return numOfRepos;
    }

    public static void setNumOfRepos(int num) {
        numOfRepos = num;
    }

    public static GitHub connect(String token) {
        GitHub gitHub= null;
        try {
            gitHub = new GitHubBuilder().withOAuthToken(token).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gitHub;
    }
    public static ArrayList getRepositories(GitHub github) throws IOException {
        PagedIterable publicRepos=github.getMyself().listRepositories();
        return (ArrayList) publicRepos.asList();
    }
}
