package okon.ASE9.raport;

public class ReportFormatterTickOS extends ReportFormatter {
    private final StringBuilder format = new StringBuilder("%-19s" + "%16s" + "%16s" + "%20s" + "%18s" + "%16s" + "%19s" + "%17s");

    @Override
    public String format(String[] input) {
        return String.format(format.toString(), input);
    }
}
