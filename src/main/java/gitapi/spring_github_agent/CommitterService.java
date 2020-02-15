package gitapi.spring_github_agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@org.springframework.stereotype.Service
public class CommitterService {

    @Autowired
    public final CommitterRepository committerRepository;
    public CommitterService(CommitterRepository committerRepository){
        this.committerRepository=committerRepository;
    }
    public void createCommitter(Committer author){
        committerRepository.save(author);
    }
}
