package gitapi.spring_github_agent.services;

import gitapi.spring_github_agent.repositories.IssueEventRepository;
import gitapi.spring_github_agent.tables.Issueevent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueEventService {
    @Autowired
    private IssueEventRepository issueEventRepository;
    public void createIssueEvent(Issueevent issueEvent){
        issueEventRepository.save(issueEvent);
    }

    public IssueEventRepository getIssueEventRepository(){
        return issueEventRepository;
    }

    public void deleteIssueEvents(){
        issueEventRepository.deleteAll();
    }

    public void setIssueEventRepository(IssueEventRepository issueEventRepository) {
        this.issueEventRepository = issueEventRepository;
    }
}
