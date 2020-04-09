package gitapi.spring_github_agent.services;

import gitapi.spring_github_agent.repositories.IssueRepository;
import gitapi.spring_github_agent.tables.Githubissue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueService {
    @Autowired
    private IssueRepository issueRepository;
    public void createIssue(Githubissue issue){
        issueRepository.save(issue);
    }

    public void deleteIssues(){
        issueRepository.deleteAll();
    }

    public IssueRepository getIssueRepository(){
        return issueRepository;
    }

    public void setIssueRepository(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }
}

