package okon.ASE9.service;

public class PerformanceExtractionStandard extends Extraction {
    private String cpuBusy = "";
    private String ioBusy = "";
    private String idle = "";

    public String getCpuBusy() { return cpuBusy; }

    public void setCpuBusy(String cpuBusy) {
        this.cpuBusy = cpuBusy;
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
