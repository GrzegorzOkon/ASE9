package okon.ASE9;

public class Job {
    private final String ip;
    private final int port;
    private final String login;
    private final String passsword;
    private final String alias;

    public Job(String ip, int port, String login, String passsword, String alias) {
        this.ip = ip;
        this.port = port;
        this.login = login;
        this.passsword = passsword;
        this.alias = alias;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getLogin() {
        return login;
    }

    public String getPasssword() {
        return passsword;
    }

    public String getAlias() {
        return alias;
    }
}
