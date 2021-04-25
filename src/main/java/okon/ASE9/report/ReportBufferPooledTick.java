package okon.ASE9.report;

import okon.ASE9.service.DataExtraction;
import okon.ASE9.service.PerformanceExtractionPooled;

import java.text.NumberFormat;
import java.util.List;

public class ReportBufferPooledTick extends ReportBuffer {
    private final ReportFormatter formatter;
    private final List<DataExtraction> extractions;

    public ReportBufferPooledTick(ReportFormatter formatter, List<DataExtraction> extractions) {
        this.formatter = formatter;
        this.extractions = extractions;
    }

    @Override
    public String buffer() {
        StringBuilder result = new StringBuilder();
        result.append("*** " + this.extractions.get(0).getAlias() + " (" + this.extractions.get(0).getServerIP() + ") ***\n\n");
        result.append(formatter.format(new String[]{"Thread Pool", "User Busy (Tick)", "System Busy (Tick)", "IO Busy (Tick)", "Total Busy (Tick)"}) + "\n");
        result.append(formatter.format(new String[]{"-----------", "----------------", "------------------", "--------------", "-----------------"}) + "\n");
        for (DataExtraction extraction : this.extractions) {
            if (extraction instanceof PerformanceExtractionPooled) {
                String formattedRow = formatter.format(new String[]{extraction.getThreadPool(),
                        ((PerformanceExtractionPooled) extraction).getUserBusyTick() + "%", ((PerformanceExtractionPooled) extraction).getSystemBusyTick() + "%",
                        ((PerformanceExtractionPooled) extraction).getIoBusyTick() + "%", computeBusy(((PerformanceExtractionPooled) extraction).getIdleTick()) + "%"});
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