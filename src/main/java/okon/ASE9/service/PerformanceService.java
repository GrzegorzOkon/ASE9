package okon.ASE9.service;

import okon.ASE9.config.Server;

import java.util.List;

public abstract class PerformanceService {
    public abstract List<DataExtraction> reportProcessorPerformance(String time, Server server);
}