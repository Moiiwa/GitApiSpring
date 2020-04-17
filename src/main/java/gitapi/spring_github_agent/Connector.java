package gitapi.spring_github_agent;
import gitapi.spring_github_agent.services.CommitService;
import gitapi.spring_github_agent.services.IssueEventService;
import gitapi.spring_github_agent.services.IssueService;
import gitapi.spring_github_agent.tables.Githubcommit;
import gitapi.spring_github_agent.tables.Githubissue;
import gitapi.spring_github_agent.tables.Githubissueevent;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class Connector {
    private static int numOfRepos;
    public static Metrics metrics[];
    protected static String token;
    private static boolean tokenOk=true;
    static String reponame;
    private GitHub gitHub;

    public GitHub getGithub(){
        return gitHub;
    }

    public  void connect(String token, String reponame) throws IOException {

        Connector.reponame=reponame;
        Connector.token=token;
        //token="c12d0ed47b1b71b40db3893b023ed48333826806";
            gitHub = connect(token);
            ArrayList repositories = getRepositories(gitHub);
            setNumOfRepos(repositories.size());
            metrics = new Metrics[getNumOfRepos()];
            initializeMetrics(metrics);
            setRepoNames(metrics, repositories);
            setCommits(metrics, repositories);
            setIssues(metrics, repositories);
            setIssueEvents(metrics, repositories);
            System.out.println();

    }
    public static boolean returnTokenOk(){
        return tokenOk;
    }
    public String getToken(){
        return token;
    }
    public String getReponame(){
        return reponame;
    }
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

    }
    @Autowired
    public IssueEventService issueEventService;

    @Autowired
    public  CommitService commitService;

    @Autowired
    public  IssueService issueService;

    public static void initializeMetrics(Metrics[] metrics){
        for (int i=0;i<getNumOfRepos();i++){
            metrics[i]=new Metrics();
        }
    }

    public  void setCommits(Metrics[] metrics,ArrayList<GHRepository> repositories) throws IOException {
        for(int i=0;i<getNumOfRepos();++i) {
            if (metrics[i].getRepositoryName().equals(reponame)) {
                metrics[i].setCommits(getCommits(repositories.get(i)));
                for (int j = 0; j < metrics[i].getCommits().size(); j++) {

                    Githubcommit commit = new Githubcommit(metrics[i].getCommit(j), reponame);
                    commitService.createCommit(commit);
                }
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

    public  void setIssueEvents(Metrics[] metrics,ArrayList<GHRepository> repositories) throws IOException {
        for(int i=0;i<getNumOfRepos();++i) {
            if (metrics[i].getRepositoryName().equals(reponame)) {
                metrics[i].setIssueEvents(getIssueEvents(repositories.get(i)));
                for (int j = 0; j < getIssueEvents(repositories.get(i)).size(); j++) {
                    GHIssueEvent ghIssueEvent = (GHIssueEvent) getIssueEvents(repositories.get(i)).get(j);
                    Githubissueevent issueevent = new Githubissueevent(ghIssueEvent,reponame);
                    issueEventService.createIssueEvent(issueevent);
                }
            }
        }
    }


    public static ArrayList getIssueEvents(GHRepository repository) throws IOException {
        ArrayList<GHIssueEvent> issues;
        issues=(ArrayList<GHIssueEvent>) repository.listIssueEvents().asList();
        return issues;
    }

    public  void setIssues(Metrics[] metrics,ArrayList<GHRepository> repositories) throws IOException {
        for(int i=0;i<getNumOfRepos();++i) {
            if (metrics[i].getRepositoryName().equals(reponame)) {
                metrics[i].setIssues(getIssues(repositories.get(i)));
                for (int j = 0; j < getIssues(repositories.get(i)).size(); j++) {
                    GHIssue ghIssue = (GHIssue) getIssues(repositories.get(i)).get(j);
                    Githubissue issue = new Githubissue(ghIssue, reponame);
                    issueService.createIssue(issue);

                }
            }
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
