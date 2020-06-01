package okon.ASE9;

import java.text.NumberFormat;

public class PerformanceReportFormatter {
    private final PerformanceReport message;

    public PerformanceReportFormatter(PerformanceReport message) {
        this.message = message;
    }

    public String format() {
        String result = String.format("%-16s %-17s %8s %%    %-12s",
                message.getServerIP(), getThreadPoolDescription(), getCPUBusy(message.getIdle()), getDescriptiveStatus(getCPUBusy(message.getIdle())));
        return result;
    }

    private String getThreadPoolDescription() {
        return isThreadPoolPresent() == true ? message.getThreadPool() : "----";
    }

    private boolean isThreadPoolPresent() {
        if (message.getThreadPool() != null) {
            return true;
        } else {
            return false;
        }
    }

    public Float getCPUBusy(String cpuIdle) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(1);
        return Float.valueOf(nf.format(100.0 - Float.valueOf(cpuIdle)).replace(',', '.'));
    }

    public String getDescriptiveStatus(Float cpuBusy) {
        String result = null;
        if (cpuBusy == 0.0) {
            result = "TOO LOW!";
        } else if (cpuBusy < 5.0) {
            result = "LOW";
        } else if (cpuBusy < 75.0) {
            result = "MID";
        } else if (cpuBusy < 100.0) {
            result = "HIGH";
        } else {
            result = "TOO HIGH!";
        }
        return result;
    }
}