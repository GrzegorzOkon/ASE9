package okon.ASE9;

public class Message {
    private String serverName;
    private String threadPool;
    private String userBusy;
    private String systemBusy;
    private String ioBusy;
    private String idle;

    public Message() {
    }

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

    public String getUserBusy() {
        return userBusy;
    }

    public void setUserBusy(String userBusy) {
        this.userBusy = userBusy;
    }

    public String getSystemBusy() {
        return systemBusy;
    }

    public void setSystemBusy(String systemBusy) {
        this.systemBusy = systemBusy;
    }

    public String getIoBusy() {
        return ioBusy;
    }

    public void setIoBusy(String ioBusy) {
        this.ioBusy = ioBusy;
    }

    public String getIdle() {
        return idle;
    }

    public void setIdle(String idle) {
        this.idle = idle;
    }
}
