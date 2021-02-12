package okon.ASE9.service;

import okon.ASE9.Job;
import okon.ASE9.db.GatewayToSybase;
import okon.ASE9.exception.AppException;

public class PerformanceServiceFactory {
    public static PerformanceService make(Job job, GatewayToSybase db) {
        if (job.getServer().getSettings().get(0).getType().equals("processor pool") &&
                job.getServer().getSettings().get(0).getValue().equals("yes")) {
            return new PooledPerformanceService(db);
        } else if (job.getServer().getSettings().get(0).getType().equals("processor pool") &&
                job.getServer().getSettings().get(0).getValue().equals("no")) {
            return new StandardPerformanceService(db);
        } else {
            throw new AppException("Nieobsługiwany parametr " + job.getServer().getSettings().get(0).getType());
        }
    }
}