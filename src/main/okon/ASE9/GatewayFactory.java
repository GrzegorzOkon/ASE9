package okon.ASE9;

import javax.sql.DataSource;

public class GatewayFactory {

    public static SybGateway make(DataSource dataSource) {
        return new SybGateway(dataSource);
    }
}