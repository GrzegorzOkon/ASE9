package okon.ASE9.report;

import okon.ASE9.messages.DataExtraction;
import okon.ASE9.messages.PerformanceExtractionPooled;
import okon.ASE9.messages.PerformanceExtractionStandard;

import java.util.List;

public class ReportBufferFactory {
    public ReportBuffer make(List<DataExtraction> extraction, String format, ReportFormatter formatter) {
        if (extraction.get(0) instanceof PerformanceExtractionPooled && format.toUpperCase().equals("TICK")) {
            return new ReportBufferPooledTick(formatter, extraction);
        } else if (extraction.get(0) instanceof PerformanceExtractionPooled && format.toUpperCase().equals("TICK, OS")) {
            return new ReportBufferPooledTickOS(formatter, extraction);
        } else if (extraction.get(0) instanceof PerformanceExtractionPooled) {
            return new ReportBufferPooledOS(formatter, extraction);
        } else if (extraction.get(0) instanceof PerformanceExtractionStandard) {
            return new ReportBufferStandardTick(formatter, extraction);
        }
        return null;
    }
}