package okon.ASE9;

public class Report {
    private String alias = "";
    private String serverIP = "";
    private String serverName = "";
    private String threadPool = "";
    private String cpuBusy = "";
    private String userBusyTick = "";
    private String systemBusyTick = "";
    private String ioBusyTick = "";
    private String idleTick = "";
    private String userBusyOS = "";
    private String systemBusyOS = "";
    private String idleOS = "";

    public Report() {}

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

    public String getUserBusyTick() {
        return userBusyTick;
    }

    public void setUserBusyTick(String userBusyTick) {
        this.userBusyTick = userBusyTick;
    }

    public String getSystemBusyTick() {
        return systemBusyTick;
    }

    public void setSystemBusyTick(String systemBusyTick) {
        this.systemBusyTick = systemBusyTick;
    }

    public String getIoBusyTick() {
        return ioBusyTick;
    }

    public void setIoBusyTick(String ioBusyTick) {
        this.ioBusyTick = ioBusyTick;
    }

    public String getIdleTick() {
        return idleTick;
    }

    public void setIdleTick(String idleTick) {
        this.idleTick = idleTick;
    }

    public String getUserBusyOS() { return userBusyOS; }

    public void setUserBusyOS(String userBusyOS) { this.userBusyOS = userBusyOS; }

    public String getSystemBusyOS() { return systemBusyOS; }

    public void setSystemBusyOS(String systemBusyOS) { this.systemBusyOS = systemBusyOS; }

    public String getIdleOS() { return idleOS; }

    public void setIdleOS(String idleOS) { this.idleOS = idleOS; }
}
