package okon.ASE9.service;

import okon.ASE9.Report;
import okon.ASE9.Server;

import java.util.List;

public abstract class PerformanceService {
    public abstract List<Report> reportProcessorPerformance(String time, Server server);
}