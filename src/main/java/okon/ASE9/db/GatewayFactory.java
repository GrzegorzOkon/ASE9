package okon.ASE9.db;

import okon.ASE9.Job;

public class GatewayFactory {
    public static GatewayToSybase make(Job job) {
        return new GatewayToSybase(job);
    }
}