package okon.ASE9.service;

import okon.ASE9.Extraction;
import okon.ASE9.Server;

import java.util.List;

public abstract class PerformanceService {
    public abstract List<Extraction> reportProcessorPerformance(String time, Server server);
}