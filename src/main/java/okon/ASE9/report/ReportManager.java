package okon.ASE9.report;

import okon.ASE9.ASE9App;
import okon.ASE9.service.DataExtraction;

import java.util.ArrayList;
import java.util.List;
import static okon.ASE9.ASE9App.extractions;

public class ReportManager {
    public void print() {
        new Printer().print((buffer(customizeReports())).trim());
    }

    private List<Report> customizeReports() {
        List<Report> result = new ArrayList<>();
        for (List<DataExtraction> extraction : extractions) {
            ReportFormatter customizedFormatter = new ReportFormatterFactory().make(extraction, ASE9App.parameters.getProperty("ReportFormat"));
            ReportBuffer customizedBuffer = new ReportBufferFactory().make(extraction, ASE9App.parameters.getProperty("ReportFormat"), customizedFormatter);
            result.add(new Report(extraction, customizedBuffer));
        }
        return result;
    }

    private String buffer(List<Report> reports) {
        StringBuilder result = new StringBuilder();
        for(Report report : reports) {
            result.append(report.getCustomizedBuffer().buffer());
        }
        return result.toString();
    }
}