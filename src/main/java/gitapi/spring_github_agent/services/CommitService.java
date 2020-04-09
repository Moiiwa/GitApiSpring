package gitapi.spring_github_agent.services;

import gitapi.spring_github_agent.repositories.CommitsRepository;
import gitapi.spring_github_agent.tables.Githubcommit;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class CommitService {

    @Autowired
     private CommitsRepository commitsRepository;


    public void createCommit(Githubcommit commit){
        commitsRepository.save(commit);
    }

    public void deleteCommits(){
        commitsRepository.deleteAll();
    }

    public CommitsRepository getCommitsRepository(){
        return commitsRepository;
    }
    public void setCommitsRepository(CommitsRepository repository){
        this.commitsRepository=repository;
    }
}
