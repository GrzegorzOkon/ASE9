package okon.ASE9;

import java.text.NumberFormat;

import static okon.ASE9.ASE9App.messages;

public class ReportFormatter {
    private final StringBuilder format = new StringBuilder();

    public ReportFormatter() {
        initialize();
    }

    private void initialize() {
        int[] sizes = checkColumnSizes();
        setFormat(sizes);
    }

    public int[] checkColumnSizes() {
        int[] result = new int[]{6, 11, 8};
        for (Report report : messages) {
            if (Integer.valueOf(report.getAlias().length()) + Integer.valueOf(report.getServerIP().length()) + 3 > result[0]) {
                result[0] = Integer.valueOf(report.getAlias().length()) +  Integer.valueOf(report.getServerIP().length()) + 3;
            }
            if (Integer.valueOf(report.getThreadPool().length()) > result[1]) {
                result[1] =  Integer.valueOf(report.getThreadPool().length());
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