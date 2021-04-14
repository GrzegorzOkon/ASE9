package okon.ASE9.raport;

public class ReportFormatterFactory {
    public ReportFormatter make(String format) {
        if (format.toUpperCase().equals("TICK")) {
            return new ReportFormatterTick();
        } else if (format.toUpperCase().equals("TICK, OS")) {
            return new ReportFormatterTickOS();
        } else {
            return new ReportFormatterOS();
        }
    }
}
