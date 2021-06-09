package okon.ASE9.report;

import okon.ASE9.WorkingEnvironment;
import okon.ASE9.messages.DataExtraction;

import java.util.ArrayList;
import java.util.List;
import static okon.ASE9.App.extractions;

public class ReportManager {
    public void print() {
        new Printer().print((buffer(customizeReports())).trim());
    }

    private List<Report> customizeReports() {
        List<Report> result = new ArrayList<>();
        for (List<DataExtraction> extraction : extractions) {
            ReportFormatter customizedFormatter = new ReportFormatterFactory().make(extraction, WorkingEnvironment.getReportType());
            ReportBuffer customizedBuffer = new ReportBufferFactory().make(extraction, WorkingEnvironment.getReportType(), customizedFormatter);
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