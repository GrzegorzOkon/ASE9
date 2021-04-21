package okon.ASE9.report;

import okon.ASE9.service.Extraction;

import java.util.List;

public class Report {
    private final List<Extraction> extraction;
    private final ReportPrinter customizedPrinter;

    public Report(List<Extraction> extraction, ReportPrinter customizedPrinter) {
        this.extraction = extraction;
        this.customizedPrinter = customizedPrinter;
    }

    public List<Extraction> getExtraction() {
        return extraction;
    }

    public ReportPrinter getCustomizedPrinter() {
        return customizedPrinter;
    }
}
