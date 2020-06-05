package okon.ASE9;

import java.text.NumberFormat;

public class PerformanceReportFormatter {
    private final PerformanceReport message;

    public PerformanceReportFormatter(PerformanceReport message) {
        this.message = message;
    }

    public String format() {
        String result = String.format("%-16s %-17s %8s %%", message.getServerIP(), getThreadPoolDescription(), getCPUBusy(message.getIdle()));
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
}