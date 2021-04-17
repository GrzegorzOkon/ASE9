package okon.ASE9.report;

public class ReportFormatterPooledTickOS extends ReportFormatter {
    private final StringBuilder format = new StringBuilder("%-19s" + "%16s" + "%16s" + "%20s" + "%18s" + "%16s" + "%19s" + "%17s");

    @Override
    public String format(String[] input) {
        return String.format(format.toString(), input);
    }
}
