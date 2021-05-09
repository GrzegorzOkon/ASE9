package okon.ASE9.report;

import okon.ASE9.messages.PerformanceExtractionPooled;
import okon.ASE9.messages.DataExtraction;

import java.text.NumberFormat;
import java.util.List;

public class ReportBufferPooledOS extends ReportBuffer {
    private final ReportFormatter formatter;
    private final List<DataExtraction> extractions;

    public ReportBufferPooledOS(ReportFormatter formatter, List<DataExtraction> extraction) {
        this.formatter = formatter;
        this.extractions = extraction;
    }

    @Override
    public String buffer() {
        StringBuilder result = new StringBuilder();
        result.append("*** " + this.extractions.get(0).getAlias() + " (" + this.extractions.get(0).getServerIP() + ") ***\n\n");
        result.append(formatter.format(new String[]{"Thread Pool", "User Busy (OS)", "System Busy (OS)", "Total Busy (OS)"}) + "\n");
        result.append(formatter.format(new String[]{"-----------", "--------------", "----------------", "---------------"}) + "\n");
        for (DataExtraction extraction : this.extractions) {
            if (extraction instanceof PerformanceExtractionPooled) {
                String formattedRow = formatter.format(new String[]{extraction.getThreadPool(),
                        ((PerformanceExtractionPooled) extraction).getUserBusyOS() + "%", ((PerformanceExtractionPooled) extraction).getSystemBusyOS() + "%",
                        computeBusy(((PerformanceExtractionPooled) extraction).getIdleOS()) + "%"});
                result.append(formattedRow + "\n");
            }
        }
        result.append("\n\n");
        return result.toString();
    }

    private Float computeBusy(String idle) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(1);
        return Float.valueOf(nf.format(100.0 - Float.valueOf(idle)).replace(',', '.'));
    }
}

    /*static void printWithThreadPoolColumnToConsole() {
        ReportFormatter formatter = new ReportFormatter(true);
        System.out.println(formatter.format(new String[]{"Server", "Thread Pool", "CPU Busy"}));
        System.out.println(formatter.format(new String[]{"------", "-----------", "--------"}));
        for (Report report : extractions) {
            if (report instanceof PerformanceReport) {
                String formattedRow = formatter.format(new String[]{report.getAlias() + " (" + report.getServerIP() + ")",
                        report.getThreadPool(), formatter.getCPUBusy(report.getIdleTick()) + " %"});
                System.out.println(formattedRow);
            } else if (report instanceof ExceptionReport) {
                String formattedRow = formatter.format(new String[]{CONNECTION_EXCEPTION_COMMUNICATE + " " + report.getAlias() +
                        " (" + report.getServerIP() + ")", "", ""});
                System.out.println(formattedRow);
            }
        }
    }*/