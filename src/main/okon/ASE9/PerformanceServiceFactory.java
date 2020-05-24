package okon.ASE9;

import okon.ASE9.exception.AppException;

public class PerformanceServiceFactory {
    public static PerformanceService make(Job job, GatewaySybase db) {
        if (job.getServer().getSettings().get(0).getType().equals("processor pool") &&
                job.getServer().getSettings().get(0).getValue().equals("yes")) {
            return new PerformanceServicePooled(db);
        } else if (job.getServer().getSettings().get(0).getType().equals("processor pool") &&
                job.getServer().getSettings().get(0).getValue().equals("no")) {
            return new PerformanceServiceStandard(db);
        } else {
            throw new AppException("Nieobsługiwany parametr " + job.getServer().getSettings().get(0).getType());
        }
    }
}