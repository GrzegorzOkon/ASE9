package okon.ASE9;

import java.text.NumberFormat;
import java.util.List;

public class ReportPrinter {
    public void print(List<Report> extractions) {
        printToConsole(extractions);
        //printToFile(extractions);
    }

    private void printToConsole(List<Report> extractions) {
        ReportFormatter formatter = new ReportFormatter();
        System.out.println(extractions.get(0).getAlias() + " {" + extractions.get(0).getServerIP() + ")\n");
        System.out.println(formatter.format(new String[]{"Thread Pool", "User Busy (OS)", "System Busy (OS)", "Total Busy (OS)"}));
        System.out.println(formatter.format(new String[]{"-----------", "--------------", "----------------", "---------------"}));
        for (Report report : extractions) {
            if (report instanceof PerformanceReport) {
                String formattedRow = formatter.format(new String[]{report.getThreadPool(),
                        ((PerformanceReport) report).getUserBusyOS() + "%", ((PerformanceReport) report).getSystemBusyOS() + "%",
                        computeBusy(((PerformanceReport) report).getIdleOS()) + "%"});
                System.out.println(formattedRow);
            }
        }
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

/*    private void printToFile(List<Result> extractions) {
        try (Writer out = new FileWriter(new java.io.File(ASE1App.getJarFileName() + ".txt"))) {
            for (int i = 0; i < extractions.size(); i++) {
                out.write("*** " + extractions.get(i).getServer().getAlias() + " (" + extractions.get(i).getServer().getIp() + ") ***");
                out.write(System.getProperty("line.separator"));
                out.write(System.getProperty("line.separator"));
                out.write("Baza danych        Wolne na dane              Wolne w logu");
                out.write(System.getProperty("line.separator"));
                out.write("-----------        -----------------------    -----------------------");
                out.write(System.getProperty("line.separator"));
                for (Space space : extractions.get(i).getSpace()) {
                    ReportFormatter formatter = new ReportFormatter();
                    out.write(formatter.format(new String[]{
                            space.getDatabase(),
                            convertToConvenientUnit(space.getFreeData()),
                            String.format("%.02f", space.getFreePercentData()) + " %",
                            convertToConvenientUnit(space.getFreeLog()),
                            String.format("%.02f", space.getFreePercentLog()) + " %"}));
                    out.write(System.getProperty("line.separator"));
                }
                if (!isLastIteration(i + 1, extractions.size())) {
                    out.write(System.getProperty("line.separator"));
                    out.write(System.getProperty("line.separator"));
                }
            }
        } catch (Exception e) {
            throw new AppException(e);
        }
    }*/