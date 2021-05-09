package okon.ASE9.service;

import okon.ASE9.db.Gateway;
import okon.ASE9.db.GatewayToSybase;

public class PerformanceServiceFactory {
    public static PerformanceService make(Gateway db) {
        if (db instanceof GatewayToSybase) {
            return new SybPerformanceService(db);
        }
        return null;
    }
}