package okon.ASE9.service;

public class PerformanceExtractionPooled extends Extraction {
    private String userBusyTick = "";
    private String systemBusyTick = "";
    private String ioBusyTick = "";
    private String idleTick = "";
    private String userBusyOS = "";
    private String systemBusyOS = "";
    private String idleOS = "";

    public PerformanceExtractionPooled() {}

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
