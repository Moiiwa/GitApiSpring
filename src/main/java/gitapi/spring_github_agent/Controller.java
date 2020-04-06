package gitapi.spring_github_agent;

import gitapi.spring_github_agent.repositories.CommitsRepository;
import gitapi.spring_github_agent.repositories.IssueEventRepository;
import gitapi.spring_github_agent.repositories.IssueRepository;
import gitapi.spring_github_agent.tables.Commit;
import gitapi.spring_github_agent.tables.Issue;
import gitapi.spring_github_agent.tables.Issueevent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    private CommitsRepository commitsRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private IssueEventRepository issueEventRepository;
    @GetMapping("/commits/all")
    public List<Commit> allCommits() {
            return commitsRepository.findAllAuthors();
    }

    @GetMapping("/issues/all")
    public List<Issue> allIssues(){
        return issueRepository.findAll();
    }

    @GetMapping("/issueEvents/all")
    public List<Issueevent> allIssueEvents(){
        return issueEventRepository.findAll();
    }
}

