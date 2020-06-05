package okon.ASE9;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PerformanceReportFormatterTest {
    PerformanceReport report;
    PerformanceReportFormatter reportFormatter;

    @Before
    public void setUp() {
        report = new PerformanceReport();
        reportFormatter = new PerformanceReportFormatter(report);
    }

    @Test
    public void shouldSayThatCpuBusyIsEquals() {
        Float targetValue = 10.5f;
        assertEquals(targetValue, reportFormatter.getCPUBusy("89.5"));
    }
}