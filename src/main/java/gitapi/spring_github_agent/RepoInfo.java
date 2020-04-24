package gitapi.spring_github_agent;

import java.io.Serializable;

public class RepoInfo implements Serializable {
    public String name;
    public String path;
    public int projectId=0;
    public String token;

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
