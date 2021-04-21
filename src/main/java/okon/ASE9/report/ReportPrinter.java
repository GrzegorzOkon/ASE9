package okon.ASE9.report;

import okon.ASE9.service.Extraction;

import java.io.Writer;
import java.util.List;

public abstract class ReportPrinter {
    public abstract void print(List<Extraction> extraction, Writer out);
}
