package okon.ASE9;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MesageFormatterTest {
    PerformanceReport message;
    PerformanceReportFormatter messageFormatter;

    @Before
    public void setUp() {
        message = new PerformanceReport();
        message.setServerName("serwer_1");
        message.setThreadPool("ThreadPool : syb_default_pool");
        message.setUserBusy("10.5");
        message.setSystemBusy("0.0");
        message.setIoBusy("8.8");
        message.setIdle("8.8");

        messageFormatter = new PerformanceReportFormatter(message);
    }

    @Test
    public void shouldSayThatOutputIsEquals() {
        String formattedString = "serwer_1             ThreadPool : syb_default_pool    10.5 %         0.0 %       8.8 %       8.8 %";

        assertEquals(formattedString, messageFormatter.format());
    }
}