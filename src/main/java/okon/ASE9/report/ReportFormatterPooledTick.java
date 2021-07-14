package okon.ASE9.report;

public class ReportFormatterPooledTick extends ReportFormatter {
    private final StringBuilder format = new StringBuilder("%-19s" + "%16s" + "%20s" + "%16s" + "%20s");

    @Override
    public String formatBody(String[] input) {
        return String.format(format.toString(), input);
    }
}
