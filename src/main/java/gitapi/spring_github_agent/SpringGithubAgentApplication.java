package gitapi.spring_github_agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@SpringBootApplication
public class SpringGithubAgentApplication {
    @Autowired
    public Service service;
    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpringGithubAgentApplication.class, args);

    }
    @EventListener(ApplicationReadyEvent.class)
    private void test() throws IOException {
        Connector connector=new Connector();
        Author author=new Author();
        author.setDate(connector.metrics[0].getCommits().get(0).getCommitDate());
        author.setName("Jack");
        author.setEmail("Jaccccc");
        service.createAuthor(author);
        System.out.println(service.string);
        service.authorRepository.findAllAuthors().forEach(it->System.out.println(it.name));
    }

}
