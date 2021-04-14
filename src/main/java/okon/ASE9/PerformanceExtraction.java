package okon.ASE9;

public class PerformanceExtraction extends Extraction {
    private String cpuBusy = "";
    private String ioBusy = "";
    private String idle = "";
    private String userBusyTick = "";
    private String systemBusyTick = "";
    private String ioBusyTick = "";
    private String idleTick = "";
    private String userBusyOS = "";
    private String systemBusyOS = "";
    private String idleOS = "";

    public PerformanceExtraction() {}

    public String getCpuBusy() { return cpuBusy; }

    public void setCpuBusy(String cpuBusy) { this.cpuBusy = cpuBusy; }

    public String getIoBusy() { return ioBusy; }

    public void setIoBusy(String ioBusy) { this.ioBusy = ioBusy; }

    public String getIdle() { return idle; }

    public void setIdle(String idle) { this.idle = idle; }

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

    public String getIdleTick() { return idleTick; }

    public void setIdleTick(String idleTick) { this.idleTick = idleTick; }

    public String getUserBusyOS() { return userBusyOS; }

    public void setUserBusyOS(String userBusyOS) { this.userBusyOS = userBusyOS; }

    public String getSystemBusyOS() { return systemBusyOS; }

    public void setSystemBusyOS(String systemBusyOS) { this.systemBusyOS = systemBusyOS; }

    public String getIdleOS() { return idleOS; }

    public void setIdleOS(String idleOS) { this.idleOS = idleOS; }
}
