package gitapi.spring_github_agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueService {
    @Autowired
    IssueRepository issueRepository;
    public void createIssue(Issue issue){
        issueRepository.save(issue);
    }
}
