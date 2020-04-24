package gitapi.spring_github_agent;

import gitapi.spring_github_agent.repositories.CommitsRepository;
import gitapi.spring_github_agent.repositories.IssueEventRepository;
import gitapi.spring_github_agent.repositories.IssueRepository;
import gitapi.spring_github_agent.services.CommitService;
import gitapi.spring_github_agent.services.IssueEventService;
import gitapi.spring_github_agent.services.IssueService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringGithubAgentApplicationTests {
    @Autowired
    private CommitsRepository commitsRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private IssueEventRepository issueEventRepository;

    //@Test()
    void validToken() throws Exception {
        Connector connector=new Connector();
        connector.issueEventService=new IssueEventService();
        connector.issueService=new IssueService();
        connector.commitService=new CommitService();
        connector.issueEventService.setIssueEventRepository(issueEventRepository);
        connector.issueService.setIssueRepository(issueRepository);
        connector.commitService.setCommitsRepository(commitsRepository);
        connector.commitService.deleteCommits();
        connector.issueService.deleteIssues();
        connector.issueEventService.deleteIssueEvents();
        connector.connect("251cb1e36448aafa45d2e29766250fe6b5330c1c","GitApiSpring");
        assert true;
    }

    //@Test
    void invalidToken() throws IOException {
        boolean indicator=false;
        Connector connector=new Connector();
        try{
            connector.connect("251cb1e36448aafa45d2e29766250fe6b5330c1","GitApiSpring");
        }catch(Exception e){
            assert true;
            indicator=true;
        }
        if(indicator==false)
            assert false;
        else
            assert true;
    }

    //@Test
    void validTokenNoReposInvalidRepoName() throws Exception {
        Connector connector=new Connector();
        connector.issueEventService=new IssueEventService();
        connector.issueService=new IssueService();
        connector.commitService=new CommitService();
        connector.issueEventService.setIssueEventRepository(issueEventRepository);
        connector.issueService.setIssueRepository(issueRepository);
        connector.commitService.setCommitsRepository(commitsRepository);
        connector.commitService.deleteCommits();
        connector.issueService.deleteIssues();
        connector.issueEventService.deleteIssueEvents();
        connector.connect("251cb1e36448aafa45d2e29766250fe6b5330c1c","CustomRepo");
        System.out.println(connector.commitService.getCommitsRepository().findAllAuthors().size());
        if(
                connector.commitService.getCommitsRepository().findAllAuthors().size()==0 &&
                connector.issueEventService.getIssueEventRepository().findAllIssueEvents().size()==0 &&
                connector.issueService.getIssueRepository().findAllIssues().size()==0)
            assert true;
        else
            assert false;
    }

    //@Test
    void validTokenValidRepoName() throws Exception{
        Connector connector=new Connector();
        connector.issueEventService=new IssueEventService();
        connector.issueService=new IssueService();
        connector.commitService=new CommitService();
        connector.issueEventService.setIssueEventRepository(issueEventRepository);
        connector.issueService.setIssueRepository(issueRepository);
        connector.commitService.setCommitsRepository(commitsRepository);
        connector.commitService.deleteCommits();
        connector.issueService.deleteIssues();
        connector.issueEventService.deleteIssueEvents();
        connector.connect("251cb1e36448aafa45d2e29766250fe6b5330c1c","GitApiSpring");
        if(connector.commitService.getCommitsRepository().findAllAuthors().size()>0 ||
                connector.issueEventService.getIssueEventRepository().findAllIssueEvents().size()>0 ||
                connector.issueService.getIssueRepository().findAllIssues().size()>0)
            assert true;
        else
            assert false;
    }
    //@Test
    void newRepository() throws Exception {
        Connector connector=new Connector();
        connector.issueEventService=new IssueEventService();
        connector.issueService=new IssueService();
        connector.commitService=new CommitService();
        connector.issueEventService.setIssueEventRepository(issueEventRepository);
        connector.issueService.setIssueRepository(issueRepository);
        connector.commitService.setCommitsRepository(commitsRepository);
        connector.connect("251cb1e36448aafa45d2e29766250fe6b5330c1c","GitApiSpring");
        int oldnumber=Connector.getNumOfRepos();
        GitHub gitHub=connector.getGithub();
        gitHub.createRepository("testRepo").create();
        connector.connect("251cb1e36448aafa45d2e29766250fe6b5330c1c","GitApiSpring");
        int newnumber=Connector.getNumOfRepos();
        if(newnumber==oldnumber+1)
            assert true;
        else
            assert false;
    }


    //@Test
    void newCommit() throws Exception {
        Connector connector=new Connector();
        connector.issueEventService=new IssueEventService();
        connector.issueService=new IssueService();
        connector.commitService=new CommitService();
        connector.issueEventService.setIssueEventRepository(issueEventRepository);
        connector.issueService.setIssueRepository(issueRepository);
        connector.commitService.setCommitsRepository(commitsRepository);
        connector.commitService.deleteCommits();
        connector.issueService.deleteIssues();
        connector.issueEventService.deleteIssueEvents();
        String reponame="GitApiSpring";
        connector.connect("251cb1e36448aafa45d2e29766250fe6b5330c1c",reponame);
        int oldsize=connector.issueService.getIssueRepository().findAllIssues().size();
        GitHub gitHub=connector.getGithub();
        ArrayList repositories=connector.getRepositories(gitHub);
        GHRepository repository = null;
        for(int i=0;i<repositories.size();i++){
            repository= (GHRepository) repositories.get(i);
            if(repository.getName().equals(reponame))
                break;
        }
        if(repository!=null) {
            connector.commitService.deleteCommits();
            connector.issueService.deleteIssues();
            connector.issueEventService.deleteIssueEvents();
            GHIssueBuilder ghIssueBuilder=repository.createIssue("test");
            ghIssueBuilder.create();
            connector.connect("251cb1e36448aafa45d2e29766250fe6b5330c1c",reponame);
            int newsize=connector.issueService.getIssueRepository().findAllIssues().size();
            if (newsize==oldsize+1)
                assert true;
            else
                assert false;
        }
        else
            assert false;
    }


    @Bean(name="entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        return sessionFactory;
    }

}
