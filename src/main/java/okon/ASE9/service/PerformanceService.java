package okon.ASE9.service;

import okon.ASE9.messages.DataExtraction;

import java.util.List;

public abstract class PerformanceService {
    public abstract List<DataExtraction> checkServerPerformance(String alias, String ip);
}