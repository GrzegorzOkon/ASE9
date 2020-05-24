package okon.ASE9;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.SQLWarning;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PerformanceServiceStandardTest {
    @Mock
    private SQLWarning sqlWarningMock;

    @Before
    public void setUp() throws Exception {
        sqlWarningMock = new SQLWarning(new SQLWarning("==============================================================================="));
        sqlWarningMock.setNextWarning(new SQLWarning("      Sybase Adaptive Server Enterprise System Performance Report"));
        sqlWarningMock.setNextWarning(new SQLWarning("==============================================================================="));
        sqlWarningMock.setNextWarning(new SQLWarning(""));
        sqlWarningMock.setNextWarning(new SQLWarning("Server Version:        Adaptive Server Enterprise/xx.x.x/ebfebf/e/eeeeee/aaa"));
        sqlWarningMock.setNextWarning(new SQLWarning("Server Name:           server_name_A"));
        sqlWarningMock.setNextWarning(new SQLWarning("Run Date:              May 09, 2020"));
        sqlWarningMock.setNextWarning(new SQLWarning("Sampling Started at:   May 09, 2020 22:09:26"));
        sqlWarningMock.setNextWarning(new SQLWarning("Sampling Ended at:     May 09, 2020 22:09:41"));
        sqlWarningMock.setNextWarning(new SQLWarning("Sample Interval:       00:00:15"));
        sqlWarningMock.setNextWarning(new SQLWarning("Sample Mode:           Reset Counters"));
        sqlWarningMock.setNextWarning(new SQLWarning(""));
        sqlWarningMock.setNextWarning(new SQLWarning("==============================================================================="));
        sqlWarningMock.setNextWarning(new SQLWarning("==============================================================================="));
        sqlWarningMock.setNextWarning(new SQLWarning(""));
        sqlWarningMock.setNextWarning(new SQLWarning("Kernel Utilization"));
        sqlWarningMock.setNextWarning(new SQLWarning("------------------"));
        sqlWarningMock.setNextWarning(new SQLWarning(""));
        sqlWarningMock.setNextWarning(new SQLWarning("  Your Runnable Process Search Count is set to 2000"));
        sqlWarningMock.setNextWarning(new SQLWarning("  and I/O Polling Process Count is set to 10"));
        sqlWarningMock.setNextWarning(new SQLWarning(""));
        sqlWarningMock.setNextWarning(new SQLWarning("  Engine Busy Utilization        CPU Busy   I/O Busy       Idle"));
        sqlWarningMock.setNextWarning(new SQLWarning("  ------------------------       --------   --------   --------"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 0                        0.0 %      0.0 %    100.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 1                        0.0 %      0.0 %    100.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 2                        0.0 %      0.0 %    100.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 3                        0.0 %      0.0 %    100.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 4                        0.0 %      0.0 %    100.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 5                        0.0 %      0.0 %    100.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 6                        0.0 %      0.0 %    100.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 7                        0.0 %      0.0 %    100.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 8                        0.0 %      0.0 %    100.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 9                        0.0 %      0.0 %    100.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 10                       0.0 %      0.0 %    100.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 11                       0.0 %      0.0 %    100.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 12                       0.0 %      0.0 %    100.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 13                       0.0 %      0.0 %    100.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 14                       0.6 %     99.4 %      0.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 15                       0.0 %      0.0 %    100.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 16                       0.0 %      0.0 %    100.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 17                       0.0 %      0.0 %    100.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 18                       0.0 %      0.0 %    100.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 19                       0.0 %      0.0 %    100.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("  ------------------------       --------   --------   --------"));
        sqlWarningMock.setNextWarning(new SQLWarning("  Summary           Total           0.6 %     99.4 %   1900.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("                  Average           0.0 %      5.0 %     95.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning(""));
        sqlWarningMock.setNextWarning(new SQLWarning("  CPU Yields by Engine            per sec      per xact       count  % of total"));
        sqlWarningMock.setNextWarning(new SQLWarning("  -------------------------  ------------  ------------  ----------  ----------"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 0                         59.3          31.8         889       5.0 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 1                         62.5          33.5         937       5.2 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 2                         62.3          33.4         935       5.2 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 3                         62.4          33.4         936       5.2 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 4                         62.4          33.4         936       5.2 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 5                         62.4          33.4         936       5.2 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 6                         62.3          33.4         935       5.2 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 7                         62.4          33.4         936       5.2 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 8                         62.1          33.3         931       5.2 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 9                         62.3          33.4         935       5.2 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 10                        62.3          33.4         935       5.2 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 11                        62.4          33.4         936       5.2 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 12                        62.3          33.4         935       5.2 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 13                        62.3          33.4         935       5.2 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 14                         9.8           5.3         147       0.8 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 15                        62.3          33.4         935       5.2 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 16                        62.2          33.3         933       5.2 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 17                        62.4          33.4         936       5.2 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 18                        62.3          33.4         934       5.2 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Engine 19                        62.4          33.4         936       5.2 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("  -------------------------  ------------  ------------  ----------"));
        sqlWarningMock.setNextWarning(new SQLWarning("  Total CPU Yields                 1191.2         638.1       17868"));
        sqlWarningMock.setNextWarning(new SQLWarning(""));
        sqlWarningMock.setNextWarning(new SQLWarning("  Network Checks"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Non-Blocking                 226313.7      121239.5     3394706      99.5 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Blocking                       1181.3         632.9       17720       0.5 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("  -------------------------  ------------  ------------  ----------"));
        sqlWarningMock.setNextWarning(new SQLWarning("  Total Network I/O Checks       227495.1      121872.4     3412426"));
        sqlWarningMock.setNextWarning(new SQLWarning("  Avg Net I/Os per Check              n/a           n/a     0.00012       n/a"));
        sqlWarningMock.setNextWarning(new SQLWarning(""));
        sqlWarningMock.setNextWarning(new SQLWarning("  Disk I/O Checks"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Total Disk I/O Checks        227741.5      122004.4     3416122       n/a"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Checks Returning I/O         181303.2       97126.7     2719548      79.6 %"));
        sqlWarningMock.setNextWarning(new SQLWarning("    Avg Disk I/Os Returned            n/a           n/a     0.00100       n/a"));
        sqlWarningMock.setNextWarning(new SQLWarning(""));
        sqlWarningMock.setNextWarning(new SQLWarning("  Tuning Recommendations for Kernel Utilization"));
        sqlWarningMock.setNextWarning(new SQLWarning("  ---------------------------------------------"));
        sqlWarningMock.setNextWarning(new SQLWarning("  - Consider decreasing the 'runnable process search count'"));
        sqlWarningMock.setNextWarning(new SQLWarning("    configuration parameter if you require the CPU's on"));
        sqlWarningMock.setNextWarning(new SQLWarning("    the machine to be used for other applications."));
    }

    @Test
    public void shouldSayThatServerStatisticsAreEqual() {
        PerformanceServiceStandard service = new PerformanceServiceStandard(null);
        PerformanceReport statistics = null;
        String correctCPUBusy = "0.0";
        String correctIOBusy = "5.0";
        String correctIdle = "95.0";
        StringBuilder warnings = new StringBuilder();
        do {
            warnings.append(sqlWarningMock.getMessage().trim()).append("\n");
            sqlWarningMock = sqlWarningMock.getNextWarning();
        } while (sqlWarningMock != null);
        statistics = service.matchStatistics(warnings.toString());
        assertEquals(correctCPUBusy, statistics.getCpuBusy());
        assertEquals(correctIOBusy, statistics.getIoBusy());
        assertEquals(correctIdle, statistics.getIdle());
    }

    @Test
    public void shouldSayThatServerNameIsEquals() {
        PerformanceServiceStandard service = new PerformanceServiceStandard(null);
        String matchedServerName = null;
        String correctServerName = "server_name_A";
        StringBuilder warnings = new StringBuilder();
        do {
            warnings.append(sqlWarningMock.getMessage().trim()).append("\n");
            sqlWarningMock = sqlWarningMock.getNextWarning();
        } while (sqlWarningMock != null);
        matchedServerName = service.matchServerName(warnings.toString());
        assertEquals(correctServerName, matchedServerName);
    }
}
