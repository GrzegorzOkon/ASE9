package okon.ASE9.report;

import okon.ASE9.messages.DataExtraction;
import okon.ASE9.messages.PerformanceExtractionPooled;
import okon.ASE9.messages.PerformanceExtractionStandard;

import java.util.List;

public class ReportFormatterFactory {
    public ReportFormatter make(List<DataExtraction> extraction, String format) {
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
