package okon.ASE9;

import okon.ASE9.service.PooledPerformanceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.SQLWarning;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PooledPerformanceServiceTest {
    PooledPerformanceService service = new PooledPerformanceService(null);

    @Mock
    private SQLWarning sqlWarningMock;

    @Before
    public void setUp() throws Exception {
        setUpWarnings();
    }

    private void setUpWarnings() {
        sqlWarningMock = new SQLWarning(new SQLWarning("	DBCC execution completed. If DBCC printed error messages, contact a user with System Administrator (SA) role.	"));
        sqlWarningMock.setNextWarning(new SQLWarning("		"));
        sqlWarningMock.setNextWarning(new SQLWarning("	DBCC execution completed. If DBCC printed error messages, contact a user with System Administrator (SA) role.	"));
        sqlWarningMock.setNextWarning(new SQLWarning("		"));
        sqlWarningMock.setNextWarning(new SQLWarning("	=============================================================================== 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Sybase Adaptive Server Enterprise System Performance Report	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	=============================================================================== 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	Server Version:        Adaptive Server Enterprise/15.7/EBF 27856 Cluster Editio 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	                       n SP139  ONE-OFF/P/x86_64/Enterprise Linux/ase157sp138x/ 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	                       4042/64-bit/FBO/Tue Dec 12 03:02:03 2017                 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	Run Date:              Feb 21, 2019                                             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	Sampling Started at:   Feb 21, 2019 18:26:24                                    	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	Sampling Ended at:     Feb 21, 2019 18:26:29                                    	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	Sample Interval:       00:00:05                                                 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	Sample Mode:           No Clear                                                 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	Counters Last Cleared: Feb 15, 2019 07:15:33                                    	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	=============================================================================== 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	Server Name:           server_name_1                                                   	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	=============================================================================== 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	=============================================================================== 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	=============================================================================== 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	Kernel Utilization	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	------------------	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Engine Utilization (Tick %)   User Busy   System Busy    I/O Busy        Idle	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------  ---------- 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  ThreadPool : aes_pool                                                         	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 11                       10.5 %         0.0 %       8.8 %      80.7 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 12                       17.5 %         0.0 %       8.8 %      73.7 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 13                       12.3 %         0.0 %       7.0 %      80.7 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 14                        7.0 %         0.0 %       8.8 %      84.2 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 15                       10.5 %         0.0 %       8.8 %      80.7 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------  ---------- 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Pool Summary        Total        57.9 %         0.0 %      42.1 %     400.0 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	                    Average        11.6 %         0.0 %       8.4 %      80.0 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  ThreadPool : intrastat_pool                                                   	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 9                         1.8 %         0.0 %       8.8 %      89.5 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 10                        1.8 %         0.0 %       8.8 %      89.5 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------  ---------- 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Pool Summary        Total         3.5 %         0.0 %      17.5 %     178.9 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	                    Average         1.8 %         0.0 %       8.8 %      89.5 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  ThreadPool : syb_default_pool                                                 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 0                         3.5 %         0.0 %       8.8 %      87.7 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 1                         3.5 %         0.0 %       8.8 %      87.7 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 2                        14.0 %         0.0 %       8.8 %      77.2 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 3                        12.3 %         0.0 %       8.8 %      78.9 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 4                         8.8 %         0.0 %       8.8 %      82.5 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 5                         3.5 %         0.0 %       8.8 %      87.7 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 6                         1.8 %         0.0 %       8.8 %      89.5 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 7                         1.8 %         0.0 %       8.8 %      89.5 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 8                         0.0 %         0.0 %       8.8 %      91.2 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------  ---------- 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Pool Summary        Total        49.1 %         0.0 %      78.9 %     771.9 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	                    Average         5.5 %         0.0 %       8.8 %      85.8 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------  ---------- 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Server Summary      Total       110.5 %         0.0 %     138.6 %    1350.9 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	                    Average         6.9 %         0.0 %       8.7 %      84.4 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Average Runnable Tasks            1 min         5 min      15 min  % of total	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------  ---------- 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  ThreadPool : aes_pool                                                         	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 11                          0.0           0.0         0.1       2.5 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 12                          0.0           0.1         0.1       6.7 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 13                          0.1           0.1         0.1      69.7 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 14                          0.0           0.1         0.1      18.0 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 15                          0.0           0.1         0.1       3.2 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Pool Summary        Total           0.1           0.3         0.4             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	                    Average           0.0           0.1         0.1             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  ThreadPool : intrastat_pool                                                   	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 9                           0.0           0.0         0.0      96.1 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 10                          0.0           0.0         0.0       3.9 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Pool Summary        Total           0.0           0.0         0.1             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	                    Average           0.0           0.0         0.0             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  ThreadPool : syb_default_pool                                                 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Global Queue                       0.0           0.0         0.0       0.0 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 0                           0.3           0.2         0.1      15.7 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 1                           0.2           0.1         0.1      12.6 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 2                           0.1           0.1         0.1       5.0 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 3                           0.2           0.2         0.1      11.3 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 4                           0.3           0.2         0.2      16.2 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 5                           0.2           0.2         0.1      11.6 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 6                           0.2           0.1         0.1      10.2 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 7                           0.2           0.2         0.1      14.6 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 8                           0.0           0.1         0.1       2.8 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Pool Summary        Total           1.7           1.3         1.2             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	                    Average           0.2           0.1         0.1             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Server Summary      Total           1.8           1.7         1.7             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	                    Average           0.1           0.1         0.1             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  CPU Yields by Engine            per sec      per xact       count  % of total	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------  ---------- 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  ThreadPool : aes_pool                                                         	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 11                                                                    	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Full Sleeps                    73.8           1.2         369       9.5 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Interrupted Sleeps             55.8           0.9         279       7.2 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 12                                                                    	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Full Sleeps                   103.8           1.7         519      13.4 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Interrupted Sleeps            110.2           1.8         551      14.2 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 13                                                                    	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Full Sleeps                    80.6           1.3         403      10.4 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Interrupted Sleeps            107.4           1.7         537      13.9 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 14                                                                    	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Full Sleeps                    48.6           0.8         243       6.3 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Interrupted Sleeps             54.2           0.9         271       7.0 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 15                                                                    	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Full Sleeps                    67.2           1.1         336       8.7 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Interrupted Sleeps             72.2           1.2         361       9.3 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Pool Summary                      773.8          12.6        3869             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  ThreadPool : intrastat_pool                                                   	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 9                                                                     	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Full Sleeps                    54.4           0.9         272      42.6 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Interrupted Sleeps             21.6           0.4         108      16.9 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 10                                                                    	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Full Sleeps                    43.4           0.7         217      34.0 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Interrupted Sleeps              8.2           0.1          41       6.4 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Pool Summary                      127.6           2.1         638             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  ThreadPool : syb_default_pool                                                 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 0                                                                     	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Full Sleeps                    89.2           1.5         446       8.1 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Interrupted Sleeps             45.6           0.7         228       4.1 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 1                                                                     	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Full Sleeps                   110.2           1.8         551      10.0 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Interrupted Sleeps             96.6           1.6         483       8.8 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 2                                                                     	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Full Sleeps                    73.4           1.2         367       6.6 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Interrupted Sleeps             42.2           0.7         211       3.8 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 3                                                                     	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Full Sleeps                    58.4           1.0         292       5.3 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Interrupted Sleeps             37.8           0.6         189       3.4 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 4                                                                     	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Full Sleeps                    67.2           1.1         336       6.1 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Interrupted Sleeps             25.8           0.4         129       2.3 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 5                                                                     	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Full Sleeps                    81.2           1.3         406       7.4 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Interrupted Sleeps             45.6           0.7         228       4.1 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 6                                                                     	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Full Sleeps                    82.6           1.3         413       7.5 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Interrupted Sleeps             24.8           0.4         124       2.2 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 7                                                                     	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Full Sleeps                    79.4           1.3         397       7.2 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Interrupted Sleeps             37.2           0.6         186       3.4 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Engine 8                                                                     	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Full Sleeps                    69.6           1.1         348       6.3 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	      Interrupted Sleeps             37.0           0.6         185       3.4 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Pool Summary                     1103.8          18.0        5519             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Total CPU Yields                 2005.2          32.7       10026             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Thread Utilization (OS %)     User Busy   System Busy        Idle	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  ThreadPool : aes_pool                                                         	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 17   (Engine 11)         10.4 %         0.2 %      89.4 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 18   (Engine 12)         17.6 %         0.6 %      81.8 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 19   (Engine 13)         16.2 %         0.6 %      83.2 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 20   (Engine 14)         11.2 %         0.4 %      88.4 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 21   (Engine 15)         13.4 %         0.4 %      86.2 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Pool Summary      Total          68.8 %         2.2 %     429.0 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	                  Average          13.8 %         0.4 %      85.8 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  ThreadPool : intrastat_pool                                                   	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 15   (Engine 9)           1.2 %         0.0 %      98.8 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 16   (Engine 10)          0.6 %         0.0 %      99.4 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Pool Summary      Total           1.8 %         0.0 %     198.2 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	                  Average           0.9 %         0.0 %      99.1 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  ThreadPool : syb_blocking_pool : no activity during sample                    	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  ThreadPool : syb_default_pool                                                 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 6    (Engine 0)           3.6 %         0.0 %      96.4 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 7    (Engine 1)          13.2 %         0.8 %      86.0 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 8    (Engine 2)           5.0 %         0.2 %      94.8 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 9    (Engine 3)          14.6 %         0.0 %      85.4 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 10   (Engine 4)           7.8 %         0.0 %      92.2 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 11   (Engine 5)           5.4 %         0.2 %      94.4 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 12   (Engine 6)           2.8 %         0.0 %      97.2 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 13   (Engine 7)           5.4 %         0.2 %      94.4 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 14   (Engine 8)           3.4 %         0.2 %      96.4 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Pool Summary      Total          61.2 %         1.6 %     837.2 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	                  Average           6.8 %         0.2 %      93.0 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  ThreadPool : syb_system_pool                                                  	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 22   (NetController)      0.0 %         0.4 %      99.6 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 23   (NetController)      0.2 %         0.2 %      99.6 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 24   (NetController)      0.6 %         0.6 %      98.8 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 25   (NetController)      0.0 %         0.2 %      99.8 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 26   (NetController)      0.2 %         0.4 %      99.4 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 27   (NetController)      0.8 %         1.0 %      98.2 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Thread 28   (DiskController)     0.2 %         0.0 %      99.8 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Pool Summary      Total           2.0 %         2.8 %     995.2 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	                  Average           0.2 %         0.3 %      99.5 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------             	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Server Summary    Total         133.8 %         6.6 %    2859.6 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	                  Average           4.5 %         0.2 %      95.3 %          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Adaptive Server threads are consuming 1.4 CPU units.                          	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Throughput (committed xacts per CPU unit) : 43.7                              	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Page Faults at OS               per sec      per xact       count  % of total	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------  ---------- 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Minor Faults                      12.0           0.2          60     100.0 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Major Faults                       0.0           0.0           0       0.0 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------  ---------- 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Total Page Faults                 12.0           0.2          60     100.0 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Context Switches at OS          per sec      per xact       count  % of total	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------  ---------- 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  ThreadPool : aes_pool                                                         	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Voluntary                        765.2          12.5        3826      21.4 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Non-Voluntary                    390.2           6.4        1951      10.9 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  ThreadPool : intrastat_pool                                                   	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Voluntary                        117.0           1.9         585       3.3 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Non-Voluntary                      1.4           0.0           7       0.0 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  ThreadPool : syb_blocking_pool                                                	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Voluntary                          0.0           0.0           0       0.0 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Non-Voluntary                      0.0           0.0           0       0.0 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  ThreadPool : syb_default_pool                                                 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Voluntary                       1002.0          16.3        5010      28.0 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Non-Voluntary                    175.0           2.9         875       4.9 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  ThreadPool : syb_system_pool                                                  	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Voluntary                       1126.2          18.3        5631      31.4 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Non-Voluntary                      5.6           0.1          28       0.2 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------  ---------- 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Total Context Switches          3582.6          58.3       17913     100.0 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  CIPC Controller Activity        per sec      per xact       count  % of total	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------  ---------- 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Polls                             0.0           0.0           0       0.0 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  CtlibController Activity        per sec      per xact       count  % of total	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------  ---------- 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Polls                             0.0           0.0           0       0.0 % 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  DiskController Activity         per sec      per xact       count  % of total	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------  ---------- 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Polls                           5460.0          88.9       27300       n/a   	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Polls Returning Events            54.6           0.9         273       1.0 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Polls Returning Max Events         0.0           0.0           0       0.0 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Total Events                      55.0           0.9         275       n/a   	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Events Per Poll                    n/a           n/a       0.010       n/a   	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  NetController Activity          per sec      per xact       count  % of total	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------  ---------- 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Polls                          76540.8        1246.6      382704       n/a   	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Polls Returning Events         72294.4        1177.4      361472      94.5 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Polls Returning Max Events         0.0           0.0           0       0.0 %	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Total Events                   72360.4        1178.5      361802       n/a   	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	   Events Per Poll                    n/a           n/a       0.945       n/a   	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Blocking Call Activity          per sec      per xact       count  % of total	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  -------------------------  ------------  ------------  ----------  ---------- 	"));
        sqlWarningMock.setNextWarning(new SQLWarning("	  Total Requests                      0.0           0.0           0       n/a  	"));
    }

    @Test
    public void shouldSayThatThreadUtilizationSectionIsEqual() {
        String correctThreadUtilizationSection = "Thread Utilization (OS %)     User Busy   System Busy        Idle\n" +
                "-------------------------  ------------  ------------  ----------\n" +
                "ThreadPool : aes_pool\n" +
                "Thread 17   (Engine 11)         10.4 %         0.2 %      89.4 %\n" +
                "Thread 18   (Engine 12)         17.6 %         0.6 %      81.8 %\n" +
                "Thread 19   (Engine 13)         16.2 %         0.6 %      83.2 %\n" +
                "Thread 20   (Engine 14)         11.2 %         0.4 %      88.4 %\n" +
                "Thread 21   (Engine 15)         13.4 %         0.4 %      86.2 %\n" +
                "-------------------------  ------------  ------------  ----------\n" +
                "Pool Summary      Total          68.8 %         2.2 %     429.0 %\n" +
                "Average          13.8 %         0.4 %      85.8 %\n" +
                "\n" +
                "ThreadPool : intrastat_pool\n" +
                "Thread 15   (Engine 9)           1.2 %         0.0 %      98.8 %\n" +
                "Thread 16   (Engine 10)          0.6 %         0.0 %      99.4 %\n" +
                "-------------------------  ------------  ------------  ----------\n" +
                "Pool Summary      Total           1.8 %         0.0 %     198.2 %\n" +
                "Average           0.9 %         0.0 %      99.1 %\n" +
                "\n" +
                "ThreadPool : syb_blocking_pool : no activity during sample\n" +
                "\n" +
                "ThreadPool : syb_default_pool\n" +
                "Thread 6    (Engine 0)           3.6 %         0.0 %      96.4 %\n" +
                "Thread 7    (Engine 1)          13.2 %         0.8 %      86.0 %\n" +
                "Thread 8    (Engine 2)           5.0 %         0.2 %      94.8 %\n" +
                "Thread 9    (Engine 3)          14.6 %         0.0 %      85.4 %\n" +
                "Thread 10   (Engine 4)           7.8 %         0.0 %      92.2 %\n" +
                "Thread 11   (Engine 5)           5.4 %         0.2 %      94.4 %\n" +
                "Thread 12   (Engine 6)           2.8 %         0.0 %      97.2 %\n" +
                "Thread 13   (Engine 7)           5.4 %         0.2 %      94.4 %\n" +
                "Thread 14   (Engine 8)           3.4 %         0.2 %      96.4 %\n" +
                "-------------------------  ------------  ------------  ----------\n" +
                "Pool Summary      Total          61.2 %         1.6 %     837.2 %\n" +
                "Average           6.8 %         0.2 %      93.0 %\n" +
                "\n" +
                "ThreadPool : syb_system_pool\n" +
                "Thread 22   (NetController)      0.0 %         0.4 %      99.6 %\n" +
                "Thread 23   (NetController)      0.2 %         0.2 %      99.6 %\n" +
                "Thread 24   (NetController)      0.6 %         0.6 %      98.8 %\n" +
                "Thread 25   (NetController)      0.0 %         0.2 %      99.8 %\n" +
                "Thread 26   (NetController)      0.2 %         0.4 %      99.4 %\n" +
                "Thread 27   (NetController)      0.8 %         1.0 %      98.2 %\n" +
                "Thread 28   (DiskController)     0.2 %         0.0 %      99.8 %\n" +
                "-------------------------  ------------  ------------  ----------\n" +
                "Pool Summary      Total           2.0 %         2.8 %     995.2 %\n" +
                "Average           0.2 %         0.3 %      99.5 %\n" +
                "\n";
        String threadUtilizationSection = null;
        try {
            StringBuilder warnings = new StringBuilder();
            do {
                warnings.append(sqlWarningMock.getMessage().trim()).append("\n");
                sqlWarningMock = sqlWarningMock.getNextWarning();
            } while (sqlWarningMock != null);
            threadUtilizationSection = service.substringThreadUtilizationSection(warnings.toString());
        } catch (Exception ex) {}
        assertEquals(correctThreadUtilizationSection, threadUtilizationSection);
    }

    @Test
    public void shouldSayThatPoolIsNotEmpty() {
        String pool = "ThreadPool : aes_pool\n" +
                "Thread 17   (Engine 11)         10.4 %         0.2 %      89.4 %\n" +
                "Thread 18   (Engine 12)         17.6 %         0.6 %      81.8 %\n" +
                "Thread 19   (Engine 13)         16.2 %         0.6 %      83.2 %\n" +
                "Thread 20   (Engine 14)         11.2 %         0.4 %      88.4 %\n" +
                "Thread 21   (Engine 15)         13.4 %         0.4 %      86.2 %\n" +
                "-------------------------  ------------  ------------  ----------\n" +
                "Pool Summary      Total          68.8 %         2.2 %     429.0 %\n" +
                "Average          13.8 %         0.4 %      85.8 %";
        assertFalse(service.isEmptyPool(pool));
    }

    @Test
    public void shouldSayThatPoolIsEmpty() {
        String pool = "ThreadPool : syb_blocking_pool : no activity during sample";
        assertTrue(service.isEmptyPool(pool));
    }

    @Test
    public void shouldSayThatThreadReportIsEqual() {
        String pool = "ThreadPool : aes_pool\n" +
                "Thread 17   (Engine 11)         10.4 %         0.2 %      89.4 %\n" +
                "Thread 18   (Engine 12)         17.6 %         0.6 %      81.8 %\n" +
                "Thread 19   (Engine 13)         16.2 %         0.6 %      83.2 %\n" +
                "Thread 20   (Engine 14)         11.2 %         0.4 %      88.4 %\n" +
                "Thread 21   (Engine 15)         13.4 %         0.4 %      86.2 %\n" +
                "-------------------------  ------------  ------------  ----------\n" +
                "Pool Summary      Total          68.8 %         2.2 %     429.0 %\n" +
                "Average          13.8 %         0.4 %      85.8 %";
        PerformanceReport correctReport = new PerformanceReport();
        correctReport.setUserBusyOS("13.8");
        correctReport.setSystemBusyOS("0.4");
        correctReport.setIdleOS("85.8");
        PerformanceReport report = service.extractThreadUsage(pool);
        assertEquals(correctReport.getUserBusyOS(), report.getUserBusyOS());
        assertEquals(correctReport.getSystemBusyOS(), report.getSystemBusyOS());
        assertEquals(correctReport.getIdleOS(), report.getIdleOS());
    }
}
