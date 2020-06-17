package okon.ASE9;

import java.text.NumberFormat;
import java.util.List;

public class PerformanceReportFormatter {
    private List<PerformanceReport> source = null;
    private final StringBuilder format = new StringBuilder();

    public PerformanceReportFormatter(List<PerformanceReport> source) {
        this.source = source;
        initialize();
    }

    private void initialize() {
        int[] sizes = checkColumnSizes();
        setFormat(sizes);
    }

    public int[] checkColumnSizes() {
        int[] result = new int[]{6, 11, 8};
        for (PerformanceReport report : source) {
            if (report.getAlias().length() + report.getServerIP().length() + 3 > result[0]) {
                result[0] = Integer.valueOf(report.getAlias().length()) +  Integer.valueOf(report.getServerIP().length()) + 3;
            }
            if (report.getThreadPool().length() > result[1]) {
                result[1] =  Integer.valueOf(report.getThreadPool().length());
            }
            if (getCPUBusy(report.getIdle()).toString().length() + 2 > result[2]) {
                result[2] =  Integer.valueOf(getCPUBusy(report.getIdle()).toString().length()) + 2;
            }
        }
        return result;
    }

    public Float getCPUBusy(String cpuIdle) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(1);
        return Float.valueOf(nf.format(100.0 - Float.valueOf(cpuIdle)).replace(',', '.'));
    }

    private void setFormat(int[] sizes) {
        format.append(("%-" + Integer.sum(sizes[0], 3) + "s" + "%-" + Integer.sum(sizes[1], 3) + "s")
                + "%" + Integer.valueOf(sizes[2]) + "s");
    }

    public String format(String[] input) {
        return String.format(format.toString(), input);
    }
}