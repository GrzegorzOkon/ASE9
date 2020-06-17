package okon.ASE9;

public class PerformanceReport {
    private String alias = "";
    private String serverIP = "";
    private String serverName = "";
    private String threadPool = "";
    private String cpuBusy = "";
    private String userBusy = "";
    private String systemBusy = "";
    private String ioBusy = "";
    private String idle = "";

    public PerformanceReport() {}

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

    public String getCpuBusy() { return cpuBusy; }

    public void setCpuBusy(String cpuBusy) { this.cpuBusy = cpuBusy; }

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
