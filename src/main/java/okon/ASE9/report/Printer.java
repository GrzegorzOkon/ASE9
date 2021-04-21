package okon.ASE9.report;

import okon.ASE9.ASE9App;
import okon.ASE9.exception.AppException;
import okon.ASE9.service.Extraction;

import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import static okon.ASE9.ASE9App.extractions;

public class Printer {
    public void print() {
        print(customizeReports());
    }

    private List<Report> customizeReports() {
        List<Report> result = new ArrayList<>();
        for (List<Extraction> extraction : extractions) {
            ReportFormatter customizedFormatter = new ReportFormatterFactory().make(extraction, ASE9App.parameters.getProperty("format"));
            ReportPrinter customizedPrinter = new ReportPrinterFactory().make(extraction, ASE9App.parameters.getProperty("format"), customizedFormatter);
            result.add(new Report(extraction, customizedPrinter));
        }
        return result;
    }

    private void print(List<Report> reports) {
        try (Writer out = new FileWriter(new java.io.File(ASE9App.getJarFileName() + ".txt"))) {
            for(Report report : reports) {
                report.getCustomizedPrinter().print(report.getExtraction(), out);
            }
        } catch (Exception e) {
            throw new AppException(e);
        }
    }
}