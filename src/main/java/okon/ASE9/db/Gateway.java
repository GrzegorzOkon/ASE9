package okon.ASE9.db;

import java.io.Closeable;
import java.sql.SQLException;
import java.sql.SQLWarning;

public abstract class Gateway implements Closeable {
    public abstract SQLWarning findLoadFor(String time) throws SQLException;

    @Override
    public void close() {
    }
}
