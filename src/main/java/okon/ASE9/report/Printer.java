package okon.ASE9.report;

import okon.ASE9.ASE9App;
import okon.ASE9.service.Extraction;

import java.util.List;

import static okon.ASE9.ASE9App.extractions;

public class Printer {
    public void print() {
        for (List<Extraction> extraction : extractions) {
            ReportFormatter customizedFormatter = new ReportFormatterFactory().make(extraction, ASE9App.parameters.getProperty("format"));
            ReportPrinter customizedPrinter = new ReportPrinterFactory().make(extraction, ASE9App.parameters.getProperty("format"), customizedFormatter);
            customizedPrinter.print();
        }
    }
}