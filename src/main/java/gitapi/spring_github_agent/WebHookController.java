package gitapi.spring_github_agent;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@org.springframework.stereotype.Controller
@RequestMapping("/webhook")
public class WebHookController{
    @Autowired
    Connector connector;
    @RequestMapping(method = RequestMethod.POST)
    public void updateEverything() throws IOException {
        connector.connect();
    }
}
