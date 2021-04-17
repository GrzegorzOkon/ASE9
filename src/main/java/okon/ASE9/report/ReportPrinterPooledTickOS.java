package okon.ASE9.report;

import okon.ASE9.ASE9App;
import okon.ASE9.service.Extraction;
import okon.ASE9.service.PerformanceExtractionPooled;
import okon.ASE9.exception.AppException;

import java.io.FileWriter;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.List;

public class ReportPrinterPooledTickOS extends ReportPrinter {
    private final ReportFormatter formatter;
    private final List<Extraction> extraction;

    public ReportPrinterPooledTickOS(ReportFormatter formatter, List<Extraction> extraction) {
        this.formatter = formatter;
        this.extraction = extraction;
    }

    @Override
    public void print() {
        printToConsole(extraction);
        printToFile(extraction);
    }

    private void printToConsole(List<Extraction> extractions) {
        System.out.println("*** " + extractions.get(0).getAlias() + " (" + extractions.get(0).getServerIP() + ") ***\n");
        System.out.println(formatter.format(new String[]{"Thread Pool", "User Busy (Tick)", "User Busy (OS)", "System Busy (Tick)", "System Busy (OS)", "IO Busy (Tick)", "Total Busy (Tick)", "Total Busy (OS)"}));
        System.out.println(formatter.format(new String[]{"-----------", "----------------", "--------------", "------------------", "----------------", "--------------", "-----------------", "---------------"}));
        for (Extraction report : extractions) {
            if (report instanceof PerformanceExtractionPooled) {
                String formattedRow = formatter.format(new String[]{report.getThreadPool(),
                        ((PerformanceExtractionPooled) report).getUserBusyTick() + "%",
                        ((PerformanceExtractionPooled) report).getUserBusyOS() + "%",
                        ((PerformanceExtractionPooled) report).getSystemBusyTick() + "%",
                        ((PerformanceExtractionPooled) report).getSystemBusyOS() + "%",
                        ((PerformanceExtractionPooled) report).getIoBusyTick() + "%",
                        computeBusy(((PerformanceExtractionPooled) report).getIdleTick()) + "%",
                        computeBusy(((PerformanceExtractionPooled) report).getIdleOS()) + "%"});
                System.out.println(formattedRow);
            }
        }
        System.out.println();
        System.out.println();
    }

    private void printToFile(List<Extraction> extractions) {
        try (Writer out = new FileWriter(new java.io.File(ASE9App.getJarFileName() + ".txt"))) {
            out.write("*** " + extractions.get(0).getAlias() + " (" + extractions.get(0).getServerIP() + ") ***");
            out.write(System.getProperty("line.separator"));
            out.write(System.getProperty("line.separator"));
            out.write(formatter.format(new String[]{"Thread Pool", "User Busy (Tick)", "User Busy (OS)", "System Busy (Tick)", "System Busy (OS)", "IO Busy (Tick)", "Total Busy (Tick)", "Total Busy (OS)"}));
            out.write(System.getProperty("line.separator"));
            out.write(formatter.format(new String[]{"-----------", "----------------", "--------------", "------------------", "----------------", "--------------", "-----------------", "---------------"}));
            for (Extraction report : extractions) {
                out.write(System.getProperty("line.separator"));
                String formattedRow = formatter.format(new String[]{report.getThreadPool(),
                        ((PerformanceExtractionPooled) report).getUserBusyTick() + "%",
                        ((PerformanceExtractionPooled) report).getUserBusyOS() + "%",
                        ((PerformanceExtractionPooled) report).getSystemBusyTick() + "%",
                        ((PerformanceExtractionPooled) report).getSystemBusyOS() + "%",
                        ((PerformanceExtractionPooled) report).getIoBusyTick() + "%",
                        computeBusy(((PerformanceExtractionPooled) report).getIdleTick()) + "%",
                        computeBusy(((PerformanceExtractionPooled) report).getIdleOS()) + "%"});
                out.write(formattedRow);
            }
            out.write(System.getProperty("line.separator"));
            out.write(System.getProperty("line.separator"));
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    private Float computeBusy(String idle) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(1);
        return Float.valueOf(nf.format(100.0 - Float.valueOf(idle)).replace(',', '.'));
    }
}