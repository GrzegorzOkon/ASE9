package okon.ASE9.report;

import okon.ASE9.messages.DataExtraction;

import java.util.List;

public class Report {
    private final List<DataExtraction> extraction;
    private final ReportBuffer customizedBuffer;

    public Report(List<DataExtraction> extraction, ReportBuffer customizedPrinter) {
        this.extraction = extraction;
        this.customizedBuffer = customizedPrinter;
    }

    public List<DataExtraction> getExtraction() {
        return extraction;
    }

    public ReportBuffer getCustomizedBuffer() {
        return customizedBuffer;
    }
}
