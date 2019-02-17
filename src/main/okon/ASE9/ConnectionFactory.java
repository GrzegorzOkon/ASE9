package okon.ASE9;

import javax.sql.DataSource;

public class ConnectionFactory {

    public SybConnection build(DataSource dataSource) {
        return new SybConnection(dataSource);
    }
}
