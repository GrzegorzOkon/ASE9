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

    @Test
    public void shouldSayThatDescriptiveStatusIsEquals() {
        assertEquals("TOO LOW!", reportFormatter.getDescriptiveStatus(0.0f));
        assertEquals("LOW", reportFormatter.getDescriptiveStatus(0.1f));
        assertEquals("MID", reportFormatter.getDescriptiveStatus(74.9f));
        assertEquals("HIGH", reportFormatter.getDescriptiveStatus(75.0f));
        assertEquals("TOO HIGH!", reportFormatter.getDescriptiveStatus(100.0f));
    }
}