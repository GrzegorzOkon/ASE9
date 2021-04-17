package okon.ASE9.report;

import okon.ASE9.service.Extraction;
import okon.ASE9.service.PerformanceExtractionPooled;
import okon.ASE9.service.PerformanceExtractionStandard;

import java.util.List;

public class ReportPrinterFactory {
    public ReportPrinter make(List<Extraction> extraction, String format, ReportFormatter formatter) {
        if (extraction.get(0) instanceof PerformanceExtractionPooled && format.toUpperCase().equals("TICK")) {
            return new ReportPrinterPooledTick(formatter, extraction);
        } else if (extraction.get(0) instanceof PerformanceExtractionPooled && format.toUpperCase().equals("TICK, OS")) {
            return new ReportPrinterPooledTickOS(formatter, extraction);
        } else if (extraction.get(0) instanceof PerformanceExtractionPooled) {
            return new ReportPrinterPooledOS(formatter, extraction);
        } else if (extraction.get(0) instanceof PerformanceExtractionStandard) {
            return new ReportPrinterStandardTick(formatter, extraction);
        }
        return null;
    }
}