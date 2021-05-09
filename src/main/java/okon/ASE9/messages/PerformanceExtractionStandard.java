package okon.ASE9.messages;

public class PerformanceExtractionStandard extends DataExtraction {
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
