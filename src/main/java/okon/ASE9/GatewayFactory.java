package okon.ASE9;

public class GatewayFactory {
    public static GatewaySybase make(Job job) {
        return new GatewaySybase(job);
    }
}