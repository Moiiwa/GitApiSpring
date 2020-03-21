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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@SpringBootApplication
public class SpringGithubAgentApplication {

    @Autowired
    private Connector connector;
    static String reponame;
    static String token;
    public static void main(String[] args) throws IOException {
        token=args[0];
        reponame=args[1];
        System.out.println(args[0]+" 1 "+args[1]);
        SpringApplication.run(SpringGithubAgentApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    private void connect() throws IOException {
        connector.connect(token,reponame);
    }

}

