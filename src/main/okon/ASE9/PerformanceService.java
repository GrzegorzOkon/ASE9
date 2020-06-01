package okon.ASE9;

import java.util.List;

public abstract class PerformanceService {
    public abstract List<PerformanceReport> reportProcessorPerformance(int seconds, String ip);
}