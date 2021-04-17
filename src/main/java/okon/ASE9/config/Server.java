package okon.ASE9.config;

import java.util.List;

public class Server {
    private final String alias;
    private final String ip;
    private final Integer port;
    private final List<Setting> settings;
    private final String authorizationInterface;

    public Server(String alias, String ip, Integer port, List<Setting> settings, String authorizationInterface) {
        this.alias = alias;
        this.ip = ip;
        this.port = port;
        this.settings = settings;
        this.authorizationInterface = authorizationInterface;
    }

    public String getAlias() {
        return alias;
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public String getAuthorizationInterface() {
        return authorizationInterface;
    }
}