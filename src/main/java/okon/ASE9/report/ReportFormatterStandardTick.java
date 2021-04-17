package okon.ASE9.report;

public class ReportFormatterStandardTick extends ReportFormatter {
    private final StringBuilder format = new StringBuilder("%8s" + "%10s" + "%12s");

    @Override
    public String format(String[] input) {
        return String.format(format.toString(), input);
    }
}
