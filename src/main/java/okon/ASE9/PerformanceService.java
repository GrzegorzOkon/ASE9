package okon.ASE9;

import java.util.List;

public abstract class PerformanceService {
    public abstract List<Report> reportProcessorPerformance(String time, Server server);
}