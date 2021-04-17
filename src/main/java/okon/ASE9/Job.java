package okon.ASE9;

import okon.ASE9.config.Authorization;
import okon.ASE9.config.Server;

public class Job {
    private final Server server;
    private final Authorization authorization;
    private final String time;

    public Job(Server server, Authorization authorization, String time) {
        this.server = server;
        this.authorization = authorization;
        this.time = time;
    }

    public Server getServer() {
        return server;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public String getTime() {
        return time;
    }
}
