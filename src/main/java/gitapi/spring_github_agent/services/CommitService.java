package gitapi.spring_github_agent.services;

import gitapi.spring_github_agent.repositories.CommitsRepository;
import gitapi.spring_github_agent.tables.Commit;
import gitapi.spring_github_agent.tables.Committer;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class CommitService {

    @Autowired
    public CommitterService committerService;

    @Autowired
    private CommitsRepository commitsRepository;


    public void createCommit(Commit commit, Committer committer){
        committerService.createCommitter(committer);
        committerService.committerRepository.save(committer);
        commitsRepository.save(commit);
    }
}
