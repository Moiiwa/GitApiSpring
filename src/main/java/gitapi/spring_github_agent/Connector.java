package gitapi.spring_github_agent;
import com.fasterxml.jackson.databind.ObjectMapper;
import gitapi.spring_github_agent.services.CommitService;
import gitapi.spring_github_agent.services.IssueEventService;
import gitapi.spring_github_agent.services.IssueService;
import gitapi.spring_github_agent.tables.Githubcommit;
import gitapi.spring_github_agent.tables.Githubissue;
import gitapi.spring_github_agent.tables.Githubissueevent;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

@Service
public class Connector {
    private static int numOfRepos;
    public static Metrics metrics[];
    protected static String token;
    private static boolean tokenOk=true;
    static String reponame;
    private GitHub gitHub;
    ArrayList<GHRepository> repositories;
    public ArrayList reponames;
    public GitHub getGithub(){
        return gitHub;
    }
    private GHHook ghHook;
    List hooksList;
    String url;
    String ip;
    public  void connect(String token) throws Exception {
        commitService.deleteCommits();
        issueService.deleteIssues();
        issueEventService.deleteIssueEvents();

        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                whatismyip.openStream()));
        ip=in.readLine();

        Connector.token=token;
            gitHub = connectWT(token);
            repositories = getRepositories(gitHub);
            reponames=new ArrayList();
            setNumOfRepos(repositories.size());
            for (int i=0;i<numOfRepos;i++){
                GHRepository rep=repositories.get(i);
                RepoInfo repoInfo=new RepoInfo();
                repoInfo.setName(rep.getName());
                repoInfo.setPath(rep.getUrl().toString());
                repoInfo.setToken(token);
                reponames.add(repoInfo);
            }

    }
    public void ChooseRep(String reponame) throws IOException {
        metrics = new Metrics[getNumOfRepos()];
        Connector.reponame=reponame;
        initializeMetrics(metrics);
        setRepoNames(metrics, repositories);
        setCommits(metrics, repositories);
        setIssues(metrics, repositories);
        setIssueEvents(metrics, repositories);
        System.out.println();
        GHRepository repository =getRepo();
        HashMap<String,String> config=new HashMap<>();
        config.put("private_token",token);
        config.put("insecure_ssl","1");
        url=new URL("http://"+ip+":9095/webhook").toExternalForm();
        config.put("url",url);
        config.put("push_events","true");
        config.put("issues_events","true");
        EnumSet<GHEvent> HOOK_EVENTS = EnumSet.of(GHEvent.PUSH,GHEvent.ISSUES);
        hooksList =  repository.getHooks();
        if(hooksList.size()==0||!hookExists())
            ghHook=repository.createHook("web",config,HOOK_EVENTS,true);
    }
    public boolean hookExists(){
        for (int i=0;i<hooksList.size();i++){
            GHHook hook= (GHHook) hooksList.get(i);
            if(hook.getConfig().containsValue(url)==true){
                return true;
            }
        }
        return false;
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

        GitHub gitHub=connectWT(token);
        ArrayList repositories=getRepositories(gitHub);
        setNumOfRepos(repositories.size());
        metrics=new Metrics[getNumOfRepos()];
        initializeMetrics(metrics);
        setRepoNames(metrics,repositories);
        setCommits(metrics,repositories);
        setIssues(metrics,repositories);
        setIssueEvents(metrics,repositories);

    }
    public GHRepository getRepo(){
        for (int i=0;i<getNumOfRepos();i++){
            GHRepository repository= (GHRepository) repositories.get(i);
            if(repository.getName().equals(reponame))
                return repository;
        }
        return null;
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

    public static GitHub connectWT(String token) {
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
