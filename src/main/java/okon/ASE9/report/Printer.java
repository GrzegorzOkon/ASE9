package okon.ASE9.report;

import okon.ASE9.ASE9App;

public class Printer {
    public void print() {
        ReportFormatter formatter = new ReportFormatterFactory().make(ASE9App.parameters.getProperty("format"));
        ReportPrinter printer = new ReportPrinterFactory().make(ASE9App.parameters.getProperty("format"), formatter);
        printer.print();
    }
}
