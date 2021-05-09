package okon.ASE9.messages;

public class DataExtraction {
    private String alias = "";
    private String serverIP = "";
    private String serverName = "";
    private String threadPool = "";

    public String getAlias() { return alias; }

    public void setAlias(String alias) { this.alias = alias; }

    public String getServerIP() { return serverIP; }

    public void setServerIP(String serverIP) { this.serverIP = serverIP; }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(String threadPool) {
        this.threadPool = threadPool;
    }
}
