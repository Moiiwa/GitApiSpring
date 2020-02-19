package gitapi.spring_github_agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueEventService {
    @Autowired
    IssueEventRepository issueEventRepository;
    public void createIssueEvent(IssueEvent issueEvent){
        issueEventRepository.save(issueEvent);
    }
}
