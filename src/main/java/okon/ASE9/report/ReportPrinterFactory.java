package okon.ASE9.report;

public class ReportPrinterFactory {
    public ReportPrinter make(String format, ReportFormatter formatter) {
        if (format.toUpperCase().equals("TICK")) {
            return new ReportPrinterTick(formatter);
        } else if (format.toUpperCase().equals("TICK, OS")) {
            return null;
        } else {
            return new ReportPrinterOS(formatter);
        }
    }
}
