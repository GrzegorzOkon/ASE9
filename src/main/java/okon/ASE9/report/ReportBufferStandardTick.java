package okon.ASE9.report;

import okon.ASE9.service.DataExtraction;
import okon.ASE9.service.PerformanceExtractionStandard;

import java.text.NumberFormat;
import java.util.List;

public class ReportBufferStandardTick extends ReportBuffer {
    private final ReportFormatter formatter;
    private final List<DataExtraction> extractions;

    public ReportBufferStandardTick(ReportFormatter formatter, List<DataExtraction> extractions) {
        this.formatter = formatter;
        this.extractions = extractions;
    }

    @Override
    public String buffer() {
        StringBuilder result = new StringBuilder();
        result.append("*** " + this.extractions.get(0).getAlias() + " (" + this.extractions.get(0).getServerIP() + ") ***\n\n");
        result.append(formatter.format(new String[]{"CPU Busy", "I/O Busy", "Total Busy"}) + "\n");
        result.append(formatter.format(new String[]{"--------", "--------", "----------"}) + "\n");
        for (DataExtraction extraction : this.extractions) {
            if (extraction instanceof PerformanceExtractionStandard) {
                String formattedRow = formatter.format(new String[]{((PerformanceExtractionStandard) extraction).getCpuBusy() + "%",
                        ((PerformanceExtractionStandard) extraction).getIoBusy() + "%",
                        computeBusy(((PerformanceExtractionStandard) extraction).getIdle()) + "%"});
                result.append(formattedRow + "\n");
            }
        }
        result.append("\n\n");
        return result.toString();
    }

    private Float computeBusy(String idle) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(1);
        return Float.valueOf(nf.format(100.0 - Float.valueOf(idle)).replace(',', '.'));
    }
}
