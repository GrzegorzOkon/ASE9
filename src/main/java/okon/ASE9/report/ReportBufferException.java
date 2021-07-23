package okon.ASE9.report;

import okon.ASE9.messages.DataExtraction;

import java.util.List;

public class ReportBufferException extends ReportBuffer {
    private final ReportFormatter formatter;
    private final List<DataExtraction> extractions;

    public ReportBufferException(ReportFormatter formatter, List<DataExtraction> extractions) {
        this.formatter = formatter;
        this.extractions = extractions;
    }

    @Override
    public String buffer() {
        StringBuilder result = new StringBuilder();
        result.append(formatter.formatHeader(new String[]{this.extractions.get(0).getAlias(), this.extractions.get(0).getServerIP()}) + "\n\n");
        result.append("Błąd połączenia do serwera");
        result.append("\n\n");
        return result.toString();
    }
}
