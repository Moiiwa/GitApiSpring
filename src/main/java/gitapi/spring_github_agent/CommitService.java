package gitapi.spring_github_agent;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class CommitService {

    @Autowired
    public CommitterService committerService;

    @Autowired
    private CommitsRepository commitsRepository;


    public void createCommit(Commit commit,Committer committer){
        committerService.createCommitter(committer);
        committerService.committerRepository.save(committer);
        commitsRepository.save(commit);
    }
}
