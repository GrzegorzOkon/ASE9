package okon.ASE9;

import okon.ASE9.exception.AppException;

public class LoadServiceFactory {
    public static LoadService make(Job job, GatewaySybase db) {
        if (job.getServer().getSettings().get(0).getType().equals("processor pool") &&
                job.getServer().getSettings().get(0).getValue().equals("yes")) {
            return new LoadServicePooled(db);
        } else {
            throw new AppException("Nieobsługiwany parametr " + job.getServer().getSettings().get(0).getType());
        }
    }
}