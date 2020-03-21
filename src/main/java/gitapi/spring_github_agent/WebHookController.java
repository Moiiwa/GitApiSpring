package gitapi.spring_github_agent;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@org.springframework.stereotype.Controller
public class WebHookController{
    @Autowired
    Connector connector;
    @GetMapping("/webhook/")
    public void updateEverything() throws IOException {
        connector.connect();
    }
}
