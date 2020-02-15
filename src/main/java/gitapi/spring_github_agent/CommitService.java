package gitapi.spring_github_agent;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class CommitService {
    @Autowired
    public CommitterService committerService;
    @Autowired
    public final CommitsRepository commitsRepository;
    public CommitService(CommitsRepository commitsRepository){
        this.commitsRepository=commitsRepository;
    }
    public void createCommit(Commit commit){
        commitsRepository.save(commit);
    }
}
