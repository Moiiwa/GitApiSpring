package gitapi.spring_github_agent;

import gitapi.spring_github_agent.repositories.CommitsRepository;
import gitapi.spring_github_agent.repositories.IssueEventRepository;
import gitapi.spring_github_agent.repositories.IssueRepository;
import gitapi.spring_github_agent.tables.Githubcommit;
import gitapi.spring_github_agent.tables.Githubissue;
import gitapi.spring_github_agent.tables.Githubissueevent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class Controller {

    private static Logger LOG;
    @Autowired
    Connector connector;
    @PostMapping("/")
    public List connect(@RequestParam(name = "auth_token") String authToken) throws Exception {
        connector.connect(authToken);
        LOG=LogManager.getLogger(SpringGithubAgentApplication.class);
        LOG.warn("Connection was successfully established!");
        return connector.reponames;
    }


    @PostMapping("/fetchdata")
    public void fetchDataForRepository(@RequestParam(name = "repo_name", required = true) String repoName) throws IOException {
        connector.ChooseRep(repoName);
        LOG.warn("Data is prepared");
    }
    @PostMapping("/webhook")
    public RedirectView webhook() throws Exception {
        connector.ChooseRep(connector.getReponame());
        LOG.warn("Data is prepared");
        return new RedirectView("/");
    }
    @Autowired
    private CommitsRepository commitsRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private IssueEventRepository issueEventRepository;
    @GetMapping("/commits")
    public List<Githubcommit> allCommits() {
            return commitsRepository.findAllAuthors();
    }

    @GetMapping("/issues")
    public List<Githubissue> allIssues(){
        return issueRepository.findAll();
    }

    @GetMapping("/issueEvents")
    public List<Githubissueevent> allIssueEvents(){
        return issueEventRepository.findAll();
    }
}

