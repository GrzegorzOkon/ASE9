package okon.ASE9.report;

import okon.ASE9.service.Extraction;
import okon.ASE9.service.PerformanceExtractionPooled;
import okon.ASE9.service.PerformanceExtractionStandard;

import java.util.List;

public class ReportFormatterFactory {
    public ReportFormatter make(List<Extraction> extraction, String format) {
        if (extraction.get(0) instanceof PerformanceExtractionPooled && format.toUpperCase().equals("TICK")) {
            return new ReportFormatterPooledTick();
        } else if (extraction.get(0) instanceof PerformanceExtractionPooled && format.toUpperCase().equals("TICK, OS")) {
            return new ReportFormatterPooledTickOS();
        } else if (extraction.get(0) instanceof PerformanceExtractionPooled) {
            return new ReportFormatterPooledOS();
        } else if (extraction.get(0) instanceof PerformanceExtractionStandard) {
            return new ReportFormatterStandardTick();
        }
        return null;
    }
}
