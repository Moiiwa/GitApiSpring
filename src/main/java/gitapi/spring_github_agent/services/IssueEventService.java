package gitapi.spring_github_agent.services;

import gitapi.spring_github_agent.repositories.IssueEventRepository;
import gitapi.spring_github_agent.tables.Issueevent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueEventService {
    @Autowired
    public IssueEventRepository issueEventRepository;
    public void createIssueEvent(Issueevent issueEvent){
        issueEventRepository.save(issueEvent);
    }
}
