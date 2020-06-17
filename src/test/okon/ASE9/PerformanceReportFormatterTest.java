package okon.ASE9;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PerformanceReportFormatterTest {
    List<Report> reports;
    ReportFormatter reportFormatter;

    @Before
    public void setUp() {
        reports = new ArrayList<>();
        PerformanceReport report = new PerformanceReport();
        report.setAlias("Serwer aplikacyjny");
        report.setServerIP("xx.xx.x.xxx");
        report.setThreadPool("thread pool 1");
        report.setIdle("50.1");
        reports.add(report);
        reportFormatter = new ReportFormatter(reports);
    }

    @Test
    public void shouldSayThatColumnSizesAreEqual() {
        int[] targetValues = new int[]{32, 13, 8};
        assertEquals(targetValues[0], reportFormatter.checkColumnSizes()[0]);
        assertEquals(targetValues[1], reportFormatter.checkColumnSizes()[1]);
        assertEquals(targetValues[2], reportFormatter.checkColumnSizes()[2]);
    }

    @Test
    public void shouldSayThatCpuBusyIsEquals() {
        Float targetValue = 10.5f;
        assertEquals(targetValue, reportFormatter.getCPUBusy("89.5"));
    }

    @Test
    public void shouldSayThatFormattedStringIsEquals() {
        String formattedText = "Serwer aplikacyjny (xx.xx.x.xxx)   thread pool 1     49.9 %";
        assertEquals(formattedText, reportFormatter.format(new String[]{"Serwer aplikacyjny (xx.xx.x.xxx)", "thread pool 1", "49.9 %"}));
    }
}