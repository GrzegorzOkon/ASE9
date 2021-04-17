package okon.ASE9;

import static org.junit.Assert.*;
import okon.ASE9.report.ReportFormatterPooledTickOS;
import org.junit.Test;

public class ReportFormatterTickOSTest {
    ReportFormatterPooledTickOS formatter = new ReportFormatterPooledTickOS();

    @Test
    public void shouldSayThatRowsAreEqual() {
        String correctRow = "Thread Pool        User Busy (Tick)  User Busy (OS)  System Busy (Tick)  System Busy (OS)  IO Busy (Tick)  Total Busy (Tick)  Total Busy (OS)";
        String returnedRow = formatter.format(new String[]{"Thread Pool", "User Busy (Tick)", "User Busy (OS)", "System Busy (Tick)", "System Busy (OS)", "IO Busy (Tick)", "Total Busy (Tick)", "Total Busy (OS)"});
        assertEquals(correctRow, returnedRow);
    }
}