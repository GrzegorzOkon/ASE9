package okon.ASE9.raport;

public class ReportFormatterTick extends ReportFormatter {
    private final StringBuilder format = new StringBuilder("%-19s" + "%16s" + "%20s" + "%16s" + "%20s");

    @Override
    public String format(String[] input) {
        return String.format(format.toString(), input);
    }
}
