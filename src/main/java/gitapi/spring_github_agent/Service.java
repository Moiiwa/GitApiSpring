package gitapi.spring_github_agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@org.springframework.stereotype.Service
public class Service {

    @Autowired
    public final AuthorRepository authorRepository;
    public final String string="smth";
    public Service(AuthorRepository authorRepository){
        this.authorRepository=authorRepository;
    }
    public void createAuthor(Author author){
        authorRepository.save(author);
    }
}
