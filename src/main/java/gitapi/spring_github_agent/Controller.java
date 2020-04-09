package gitapi.spring_github_agent;

import gitapi.spring_github_agent.repositories.CommitsRepository;
import gitapi.spring_github_agent.repositories.IssueEventRepository;
import gitapi.spring_github_agent.repositories.IssueRepository;
import gitapi.spring_github_agent.tables.Commit;
import gitapi.spring_github_agent.tables.Issue;
import gitapi.spring_github_agent.tables.Issueevent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@RestController
public class Controller {
    @Autowired
    Connector connector;
    @PostMapping("/github")
    public void fetchProject(@RequestParam(name = "auth_token") String authToken, @RequestParam(name = "repo_name", required = true) String repoName) throws IOException {
        commitsRepository.deleteAll();
        issueRepository.deleteAll();
        issueEventRepository.deleteAll();
        connector.connect(authToken, repoName);
    }

    @Autowired
    private CommitsRepository commitsRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private IssueEventRepository issueEventRepository;
    @GetMapping("/github/commits")
    public List<Commit> allCommits() {
            return commitsRepository.findAllAuthors();
    }

    @GetMapping("/github/issues")
    public List<Issue> allIssues(){
        return issueRepository.findAll();
    }

    @GetMapping("/github/issueEvents")
    public List<Issueevent> allIssueEvents(){
        return issueEventRepository.findAll();
    }
}

