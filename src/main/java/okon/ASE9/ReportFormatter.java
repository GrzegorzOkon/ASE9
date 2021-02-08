package okon.ASE9;

import java.text.NumberFormat;

import static okon.ASE9.ASE9App.CONNECTION_EXCEPTION_COMMUNICATE;
import static okon.ASE9.ASE9App.messages;

public class ReportFormatter {
    private final StringBuilder format = new StringBuilder();

    public ReportFormatter(boolean threadPoolColumn) {
        initialize(threadPoolColumn);
    }

    private void initialize(boolean threadPoolColumn) {
        int[] sizes = checkColumnSizes();
        setFormat(threadPoolColumn, sizes);
    }

    public int[] checkColumnSizes() {
        int[] result = new int[]{6, 11, 8};
        for (Report report : messages) {
            if (report instanceof PerformanceReport) {
                if (checkServerColumnWidth(report) > result[0]) {
                    result[0] = checkServerColumnWidth(report);
                }
                if (checkThreadPoolColumnWidth(report) > result[1]) {
                    result[1] = checkThreadPoolColumnWidth(report);
                }
            } else if (report instanceof ExceptionReport) {
                if (checkServerColumnWidthWithException(report) > result[0]) {
                    result[0] = checkServerColumnWidthWithException(report);
                }
            }
        }
        return result;
    }

    private int checkServerColumnWidth(Report report) {
        return Integer.valueOf(report.getAlias().length()) + Integer.valueOf(report.getServerIP().length()) + 3;
    }

    private int checkThreadPoolColumnWidth(Report report) {
        return Integer.valueOf(report.getThreadPool().length());
    }

    private int checkServerColumnWidthWithException(Report report) {
        return CONNECTION_EXCEPTION_COMMUNICATE.length() + 1 + Integer.valueOf(report.getAlias().length()) +
                Integer.valueOf(report.getServerIP().length()) + 3;
    }

    public Float getCPUBusy(String cpuIdle) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(1);
        return Float.valueOf(nf.format(100.0 - Float.valueOf(cpuIdle)).replace(',', '.'));
    }

    private void setFormat(boolean threadPoolColumn, int[] sizes) {
        if (threadPoolColumn) {
            format.append(("%-" + Integer.sum(sizes[0], 3) + "s" + "%-" + Integer.sum(sizes[1], 3) + "s")
                    + "%" + Integer.valueOf(sizes[2]) + "s");
        } else {
            format.append(("%-" + Integer.sum(sizes[0], 3) + "s" + "%" + Integer.valueOf(sizes[2]) + "s"));
        }
    }

    public String format(String[] input) {
        return String.format(format.toString(), input);
    }
}